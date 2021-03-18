package mao.bishe.api.controller;

import mao.bishe.api.model.User;
import mao.bishe.api.model.query.UserQuery;
import mao.bishe.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userinfo")
public class UserController {
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/setpicture",produces = "application/json;charset=UTF-8")
    public ResponseEntity savePciture(MultipartFile file, HttpServletRequest request){
        String userName = request.getHeader("userName");
        User user = userService.findUserByStudentId(userName);
        if (file!=null){
            try {
                InputStream ins = file.getInputStream();
                byte[] buffer=new byte[1024];
                int len=0;
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                while((len=ins.read(buffer))!=-1){
                    bos.write(buffer,0,len);
                }
                bos.flush();
                byte data[] = bos.toByteArray();
                user.setPicture(data);
                userService.save(user);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok("照片设置成功");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getUser")
    public ResponseEntity getUser(HttpServletRequest request){
        String userName = request.getHeader("userName");
        User user = userService.findUserByStudentId(userName);
        UserQuery userQuery = userService.findUser(userName);
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("data",user);
        map.put("userQuery",userQuery);
        map.put("message","请求成功");
        return ResponseEntity.ok(map);
    }

}
