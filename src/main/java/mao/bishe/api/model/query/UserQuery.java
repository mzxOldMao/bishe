package mao.bishe.api.model.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserQuery implements Serializable {
    private Long id;
    private String student_id;
    private String class_id;
    private String student_name;
    private String campus;
    private String course_unit;
}
