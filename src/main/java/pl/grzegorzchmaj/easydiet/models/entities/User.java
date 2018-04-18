package pl.grzegorzchmaj.easydiet.models.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.grzegorzchmaj.easydiet.enums.PhysicalActivity;
import pl.grzegorzchmaj.easydiet.enums.Plans;
import pl.grzegorzchmaj.easydiet.enums.Sex;
import pl.grzegorzchmaj.easydiet.models.forms.RegisterForm;

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

    public User(RegisterForm registerForm){
        this.login=registerForm.getLogin();
        this.password=registerForm.getPassword();
        this.weight=registerForm.getWeight();
        this.height=registerForm.getHeight();
        this.sex=registerForm.getSex();
        this.age=registerForm.getAge();
        this.physicalActivity=registerForm.getPhysicalActivity();
        this.plans=registerForm.getPlans();
    }


}
