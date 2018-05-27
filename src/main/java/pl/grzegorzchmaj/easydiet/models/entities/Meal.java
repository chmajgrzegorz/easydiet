package pl.grzegorzchmaj.easydiet.models.entities;


import lombok.*;
import pl.grzegorzchmaj.easydiet.models.forms.MealForm;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"mealInfo", "id"})
@NoArgsConstructor
@Table(name = "meal")
@ToString(exclude = {"mealInfo"})
public class Meal {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "meal_ingredient", joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<IngredientWeight> ingredients;
    @Column(length = 1000)
    private String description;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealInfo> mealInfo;
    private Integer calories;

    public Meal(MealForm mealForm) {
        this.name = mealForm.getName();
        this.description = mealForm.getDescription();
        this.imageUrl = mealForm.getImageUrl();
        this.category = mealForm.getCategory();
    }
}

