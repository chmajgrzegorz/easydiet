package pl.grzegorzchmaj.easydiet.models.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class MealInfo {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "meal_id")
    private Meal meal;
    private String name;
    @ManyToMany
    @JoinTable(name = "diet_meal_info", joinColumns = @JoinColumn(name = "meal_info_id"),
            inverseJoinColumns = @JoinColumn(name = "diet_id"))
    private List<Diet> diet;

    public MealInfo(LocalDate date, String description) {
        this.date = date;
        this.name = description;
    }
}
