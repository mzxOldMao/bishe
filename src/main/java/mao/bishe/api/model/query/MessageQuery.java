package mao.bishe.api.model.query;

import lombok.Data;
import mao.bishe.api.model.form.PageForm;

@Data
public class MessageQuery extends PageForm {
    private Integer flag;
}
