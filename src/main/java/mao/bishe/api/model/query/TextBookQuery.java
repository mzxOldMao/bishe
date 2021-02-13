package mao.bishe.api.model.query;

import lombok.Data;
import mao.bishe.api.model.form.PriceForm;

@Data
public class TextBookQuery extends PriceForm {
    private String campus;
    private String courseUnit;
    private String courseName;
    private String bookName;
    private String author;

}
