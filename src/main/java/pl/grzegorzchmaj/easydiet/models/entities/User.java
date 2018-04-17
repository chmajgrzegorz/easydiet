package pl.grzegorzchmaj.easydiet.models.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.grzegorzchmaj.easydiet.enums.PhysicalActivity;
import pl.grzegorzchmaj.easydiet.enums.Plans;
import pl.grzegorzchmaj.easydiet.enums.Sex;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;
    private Float weight;
    private Float height;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private PhysicalActivity physicalActivity;
    @Enumerated(EnumType.STRING)
    private Plans plans;



}
