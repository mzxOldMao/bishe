package mao.bishe.api.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "textbook")
public class TextBook implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String campus;
    @Column(name = "course_unit")
    private String courseUnit;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "class_number")
    private String classNumber;
    @Column
    private Integer people;
    @Column
    private String ISBN;
    @Column(name = "book_name")
    private String bookName;
    @Column
    private String author;
    @Column
    private String publish;
    @Column
    private Double price;
}
