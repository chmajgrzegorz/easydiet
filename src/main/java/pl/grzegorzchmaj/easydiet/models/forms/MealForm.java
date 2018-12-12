package pl.grzegorzchmaj.easydiet.models.forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.grzegorzchmaj.easydiet.models.entities.Category;
import pl.grzegorzchmaj.easydiet.models.entities.Ingredient;
import pl.grzegorzchmaj.easydiet.models.entities.IngredientWeight;


import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class MealForm {

    private Long id;
    private String name;
    private List<Ingredient> ingredients;
    private String description;
    private String imageUrl;
    private Set<Category> category;
}
