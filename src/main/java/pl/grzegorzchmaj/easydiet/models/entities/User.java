package pl.grzegorzchmaj.easydiet.models.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.grzegorzchmaj.easydiet.enums.HowManyMeals;
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
    @Enumerated(EnumType.STRING)
    private HowManyMeals meals;

    public User(RegisterForm registerForm){
        this.login=registerForm.getLogin();
        this.password=registerForm.getPassword();
        this.weight=registerForm.getWeight();
        this.height=registerForm.getHeight();
        this.sex=registerForm.getSex();
        this.age=registerForm.getAge();
        this.physicalActivity=registerForm.getPhysicalActivity();
        this.plans=registerForm.getPlans();
        this.meals=registerForm.getMeals();
    }

    public User update(User user, RegisterForm registerForm){
        user.weight=registerForm.getWeight();
        user.height=registerForm.getHeight();
        user.sex=registerForm.getSex();
        user.age=registerForm.getAge();
        user.physicalActivity=registerForm.getPhysicalActivity();
        user.plans=registerForm.getPlans();
        user.meals=registerForm.getMeals();
        return user;
    }

}
