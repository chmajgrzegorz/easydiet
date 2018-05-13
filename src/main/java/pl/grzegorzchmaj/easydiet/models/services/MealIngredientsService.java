package pl.grzegorzchmaj.easydiet.models.services;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import pl.grzegorzchmaj.easydiet.models.entities.Ingredient;
import pl.grzegorzchmaj.easydiet.models.entities.IngredientWeight;
import pl.grzegorzchmaj.easydiet.models.forms.MealForm;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor
public class MealIngredientsService {


    private String name;
    private List<Ingredient> ingredients;
    private List<IngredientWeight> setIngredients = new ArrayList<>();
    private MealForm mealForm;

    public void clear() {
        this.name = null;
        this.ingredients = new ArrayList<>();
        this.setIngredients = new ArrayList<>();
        this.mealForm = null;
    }

    public Ingredient getOneIngredient(){
        Ingredient oneIngredient = ingredients.get(ingredients.size()-1);
        ingredients.remove(ingredients.size()-1);
        return oneIngredient;
    }

    public void addIngredientWeight (IngredientWeight ingredientWeight){
        setIngredients.add(ingredientWeight);
    }

    public boolean ingredientsIsNull(){
        if(ingredients.size()==0){
            return true;
        }
        return false;
    }
}
