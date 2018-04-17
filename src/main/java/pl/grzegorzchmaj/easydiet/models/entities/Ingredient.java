package pl.grzegorzchmaj.easydiet.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(name = "calories")
    private Float caloriesPer100g;
    @Column(name = "proteins")
    private Float proteinPer100g;
    @Column(name = "carbohydrates")
    private Float carbohydratePer100g;
    @Column(name = "fat")
    private Float fatPer100g;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "meal_ingredient", joinColumns = @JoinColumn(name = "ingredient_id"), inverseJoinColumns = @JoinColumn(name = "meal_id"))
    private Set<Meal> meals;

}
