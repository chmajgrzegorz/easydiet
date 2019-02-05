package pl.grzegorzchmaj.easydiet.models;

import lombok.Data;
import pl.grzegorzchmaj.easydiet.models.entities.Ingredient;
import pl.grzegorzchmaj.easydiet.models.entities.IngredientWeight;

import java.util.HashMap;
import java.util.Map;

@Data
public class ShoppingList {

    private Map<Ingredient, Long> ingredientWeights = new HashMap<>();

    public void addWeight(IngredientWeight ingredientWeight) {
        Ingredient ingredient = ingredientWeight.getIngredient();
        Long weight = ingredientWeights.getOrDefault(ingredient, 0L);
        weight += ingredientWeight.getWeight();
        ingredientWeights.put(ingredient, weight);
        System.out.println(ingredient + "   " + weight);
    }

}
