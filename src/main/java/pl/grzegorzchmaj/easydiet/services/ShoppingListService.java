package pl.grzegorzchmaj.easydiet.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import pl.grzegorzchmaj.easydiet.exceptions.ShoppingServiceException;
import pl.grzegorzchmaj.easydiet.models.ShoppingList;
import pl.grzegorzchmaj.easydiet.entities.*;

import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor

public class ShoppingListService {

    DietMealsService dietMealsService;

    @Autowired
    public ShoppingListService(DietMealsService dietMealsService) {
        this.dietMealsService = dietMealsService;
    }


    public ShoppingList createShoppingList(User user) throws ShoppingServiceException {
        List<MealInfo> meals = getMeals(user);
        dietMealsService.adjustMealsIngredients(meals,user);
        return createShoppingList(meals);
    }

    private List<MealInfo> getMeals(User user){
        Diet diet = user.getDiet();
        if (diet == null || diet.getMeals() == null || diet.getMeals().isEmpty()) {
            throw new ShoppingServiceException("User doesn't have diet");
        }
        return diet.getMeals();
    }


    private ShoppingList createShoppingList(List<MealInfo> meals){
        ShoppingList shoppingList = new ShoppingList();
        for (MealInfo mealInfo : meals) {
            processIngredientWeights(mealInfo.getMeal().getIngredientWeights(), shoppingList);
        }
        return shoppingList;
    }

    private void processIngredientWeights(List<IngredientWeight> ingredientWeights, ShoppingList shoppingList) {
        for (IngredientWeight ingredientWeight: ingredientWeights) {
            shoppingList.addWeight(ingredientWeight);
        }
    }
}
