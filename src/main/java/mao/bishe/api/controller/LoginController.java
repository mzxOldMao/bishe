package mao.bishe.api.controller;

import com.ramostear.captcha.HappyCaptcha;
import mao.bishe.api.model.User;
import mao.bishe.api.model.form.LoginForm;
import mao.bishe.api.model.form.UpdatePwd;
import mao.bishe.api.service.UserService;
import mao.bishe.api.utils.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    @RequestMapping("/getImage")
    public void getImage(HttpServletRequest reqeust, HttpServletResponse response) throws IOException {
        HappyCaptcha.require(reqeust,response).build().finish();//创建验证码
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginForm loginForm){
        System.out.println(loginForm);
        Map<String,Object> map = new HashMap<>();
        User user = userService.findUserByStudentId(loginForm.getUsername());
        stringRedisTemplate.opsForValue().set(loginForm.getUsername(),loginForm.getPassword());
        if (user!=null){
            if (user.validPassword(loginForm.getPassword())) {
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token1 = new UsernamePasswordToken(loginForm.getUsername(), loginForm.getPassword());
                try {
                    subject.login(token1);
                    String token = JWTUtil.generateToken(loginForm);
                    stringRedisTemplate.opsForValue().set("TOKEN_"+loginForm.getUsername(),token);
                    stringRedisTemplate.opsForValue().set("LoginUser"+loginForm.getUsername(),loginForm.getUsername());
                    System.out.println("登录成功！！！！！！！！！");
                    map.put("code",200);
                    map.put("token",token);
                    map.put("message","登录成功");
                    return ResponseEntity.ok(map);
                }
                //下面不用理会
                catch (UnknownAccountException e) {//用户名不存在
                    return ResponseEntity.badRequest().body("用户名不存在");
                } catch (IncorrectCredentialsException e) {
                    return ResponseEntity.badRequest().body("密码错误");
                }
            }else {
                map.put("message","密码错误");
                return ResponseEntity.ok(map);
            }
        }else {
            map.put("message","用户不存在");
            return ResponseEntity.ok(map);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/logout")
    public ResponseEntity lougout(HttpServletRequest request){
        String token = request.getHeader("ApiAuthorization");
        JWTUtil.ShiroUser shiroUser = JWTUtil.parseToken(token);
        String username = shiroUser.getUsername();
        System.out.println("username"+username);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        stringRedisTemplate.delete("TOKEN_"+username);
        stringRedisTemplate.delete("LoginUser"+username);
        Map<String,Object> map = new HashMap<>();
        map.put("message","登出成功");
        return ResponseEntity.ok(map);
    }

    /**
     * 修改密码
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/updatapwd")
    public ResponseEntity updatepwd(@RequestBody UpdatePwd updatePwd,HttpServletRequest request){
        System.out.println(updatePwd);
        String userName = request.getHeader("userName");
        System.out.println(userName);
        updatePwd.setStudentId(userName);
        User user = userService.findUserByStudentId(userName);
        Map<String,Object> map = new HashMap<>();
        if (user.validPassword(updatePwd.getOldPass())){
            userService.update(updatePwd);
            map.put("message","修改成功");
            return ResponseEntity.ok(map);
        }else {
            map.put("message","原密码不正确！");
            return ResponseEntity.ok(map);
        }
    }
}
