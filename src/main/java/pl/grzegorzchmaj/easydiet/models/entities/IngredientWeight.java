package pl.grzegorzchmaj.easydiet.models.entities;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","meals"})
@NoArgsConstructor
@Table(name = "ingredient_weight")
public class IngredientWeight {


    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ingredient_weight_id")
    private Ingredient ingredient;
    private Long weight;

    @ManyToMany
    @JoinTable(name = "meal_ingredient", joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id"))
    private Set<Meal> meals;

}
