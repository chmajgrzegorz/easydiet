package pl.grzegorzchmaj.easydiet.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import pl.grzegorzchmaj.easydiet.forms.MealForm;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"mealInfo", "id"})
@NoArgsConstructor
@Table(name = "meal")
@ToString(exclude = {"mealInfo"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "meal_ingredient", joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<IngredientWeight> ingredientWeights;
    @Column(length = 1000)
    private String description;
    private String imageUrl;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "meal_category", joinColumns = @JoinColumn(name = "meal_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonBackReference
    private Set<Category> category;
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<MealInfo> mealInfo;
    private Integer calories;

    public Meal(MealForm mealForm) {
        this.name = mealForm.getName();
        this.description = mealForm.getDescription();
        this.imageUrl = mealForm.getImageUrl();
        this.category = mealForm.getCategory();
    }
}

