package mao.bishe.api.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "book_name")
    private String bookName;
    @Column
    private String ISBN;
    @Column
    private String author;
    @Column
    private String publish;
    @Column
    private Double price;

}
