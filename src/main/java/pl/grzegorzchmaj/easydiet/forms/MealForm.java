package pl.grzegorzchmaj.easydiet.forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.grzegorzchmaj.easydiet.entities.Category;
import pl.grzegorzchmaj.easydiet.entities.Ingredient;


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
