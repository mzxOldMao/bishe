package mao.bishe.api.model.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageForm implements Serializable {
    private String name;
    private String region;
    private String des;
    private String type;
}
