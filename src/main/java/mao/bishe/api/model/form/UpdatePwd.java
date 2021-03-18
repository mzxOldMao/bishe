package mao.bishe.api.model.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePwd implements Serializable {
    private String studentId;
    private String oldPass;
    private String newPass;
    private String checkPass;
}
