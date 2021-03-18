package mao.bishe.api.model.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
public class OrderBookQuery implements Serializable {

    private Long order_id;
    private String book_name;
    private String ISBN;
    private String author;
    private String publish;
    private Double price;

    private Long book_id;

    private Integer flag;

    @JsonFormat
    private Date create_time;

    private String student_id;

    private String class_id;

    private String flagdetail;
}
