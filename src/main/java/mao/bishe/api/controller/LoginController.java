package mao.bishe.api.controller;

import com.ramostear.captcha.HappyCaptcha;
import mao.bishe.api.model.User;
import mao.bishe.api.model.form.LoginForm;
import mao.bishe.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {
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
        if (user!=null){
            if (user.validPassword(loginForm.getPassword())) {
                map.put("code",200);
                map.put("message","登录成功");
                return ResponseEntity.ok(map);
            }else {
                map.put("message","密码错误");
                return ResponseEntity.ok(map);
            }
        }else {
            map.put("message","用户不存在");
            return ResponseEntity.ok(map);
        }
    }
}
