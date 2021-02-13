package mao.bishe.api.model.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginForm implements Serializable {
    private String username;//学号
    private String password;  //密码
}
