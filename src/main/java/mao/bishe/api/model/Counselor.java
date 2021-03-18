package mao.bishe.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "counselor")
public class Counselor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "counselor_id")
    private String counselorId;
    @Column(name = "counselor_name")
    private String counselorName;
    @Column
    private String sex;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name = "stu_s")
    private String stuS;
}
