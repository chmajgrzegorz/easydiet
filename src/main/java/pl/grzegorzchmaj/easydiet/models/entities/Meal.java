package pl.grzegorzchmaj.easydiet.models.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import pl.grzegorzchmaj.easydiet.models.forms.MealForm;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "meal")
public class Meal {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(name = "meal_ingredient", joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<IngredientWeight> ingredients;
    private String description;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Meal(MealForm mealForm) {
        this.name = mealForm.getName();
        this.description = mealForm.getDescription();
        this.imageUrl = mealForm.getImageUrl();
        this.category = mealForm.getCategory();
    }
}

