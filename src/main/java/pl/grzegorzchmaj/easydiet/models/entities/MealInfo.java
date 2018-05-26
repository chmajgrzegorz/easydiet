package pl.grzegorzchmaj.easydiet.models.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","diet"})
@NoArgsConstructor
@Table(name = "meal_info")
@ToString(exclude = {"diet"})
public class MealInfo implements Comparable<MealInfo>{

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "meal_id")
    private Meal meal;
    private String name;
    @ManyToMany
    @JoinTable(name = "diet_meal_info", joinColumns = @JoinColumn(name = "meal_info_id"),
            inverseJoinColumns = @JoinColumn(name = "diet_id"))
    private List<Diet> diet;

    public MealInfo(LocalDate date, String description, Meal meal) {
        this.date = date;
        this.name = description;
        this.meal = meal;
    }

    @Override
    public int compareTo(MealInfo o) {
        return getName().compareTo(o.getName());
    }
}
