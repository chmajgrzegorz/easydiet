package pl.grzegorzchmaj.easydiet.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import pl.grzegorzchmaj.easydiet.enums.HowManyMeals;
import pl.grzegorzchmaj.easydiet.enums.PhysicalActivity;
import pl.grzegorzchmaj.easydiet.enums.Plans;
import pl.grzegorzchmaj.easydiet.enums.Sex;
import pl.grzegorzchmaj.easydiet.forms.RegisterForm;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "user")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "roles"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private HowManyMeals howManyMeals;
    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Diet diet;
    private int calories;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(RegisterForm registerForm){
        this.login=registerForm.getLogin();
        this.password=registerForm.getPassword();
        this.weight=registerForm.getWeight();
        this.height=registerForm.getHeight();
        this.sex=registerForm.getSex();
        this.age=registerForm.getAge();
        this.physicalActivity=registerForm.getPhysicalActivity();
        this.plans=registerForm.getPlans();
        this.howManyMeals=registerForm.getHowManyMeals();
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
        this.howManyMeals=registerForm.getHowManyMeals();
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
