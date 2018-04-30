package pl.grzegorzchmaj.easydiet.models.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","user"})
@NoArgsConstructor
@Table(name = "diet")
public class Diet {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;
    private String description;
    @ManyToMany
    @JoinTable(name = "diet_meal_info", joinColumns = @JoinColumn(name = "diet_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_info_id"))
    private List<MealInfo> meals;
    private Date startDate;
    private Date endDate;
}
