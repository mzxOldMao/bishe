package mao.bishe.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "test_student")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "classId")
    private String classId;
    @Column(name = "studentId")
    private String studentId;
    @Column(name = "studentName")
    private String studentName;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name = "sex")
    private String sex;
    @Column(name = "native")
    private String nati;
    @Column(name = "picture")
    private byte[] picture;
    public boolean validPassword(String password) {

        return this.password.equals(password);
    }
}
