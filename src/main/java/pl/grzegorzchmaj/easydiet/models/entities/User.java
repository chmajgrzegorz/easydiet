package pl.grzegorzchmaj.easydiet.models.entities;

import lombok.*;
import pl.grzegorzchmaj.easydiet.enums.HowManyMeals;
import pl.grzegorzchmaj.easydiet.enums.PhysicalActivity;
import pl.grzegorzchmaj.easydiet.enums.Plans;
import pl.grzegorzchmaj.easydiet.enums.Sex;
import pl.grzegorzchmaj.easydiet.models.forms.RegisterForm;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "user")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
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
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Diet diet;
    private int calories;

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
        if(registerForm.getSex().getSex()== 'K'){
            this.calories=(int)((655.1+(9.567*registerForm.getWeight())+1.85*registerForm.getHeight()
            -4.68*registerForm.getAge())*registerForm.getPhysicalActivity().getLevel()
                    +registerForm.getPlans().getCalories());
        }
        else{
            this.calories=(int)((66.47+(13.7*registerForm.getWeight())+5*registerForm.getHeight()
                    -6.76*registerForm.getAge())*registerForm.getPhysicalActivity().getLevel()
                    +registerForm.getPlans().getCalories());
        }
    }

    public void update(RegisterForm registerForm){
        this.weight=registerForm.getWeight();
        this.height=registerForm.getHeight();
        this.sex=registerForm.getSex();
        this.age=registerForm.getAge();
        this.physicalActivity=registerForm.getPhysicalActivity();
        this.plans=registerForm.getPlans();
        this.meals=registerForm.getMeals();
        if(registerForm.getSex().getSex()== 'K'){
            this.calories=(int)((655.1+(9.567*registerForm.getWeight())+1.85*registerForm.getHeight()
                    -4.68*registerForm.getAge())*registerForm.getPhysicalActivity().getLevel()
                    +registerForm.getPlans().getCalories());
        }
        else{
            this.calories=(int)((66.47+(13.7*registerForm.getWeight())+5*registerForm.getHeight()
                    -6.76*registerForm.getAge())*registerForm.getPhysicalActivity().getLevel()
                    +registerForm.getPlans().getCalories());
        }
    }

}
