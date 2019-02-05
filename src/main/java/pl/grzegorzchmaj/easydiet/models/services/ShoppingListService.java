package pl.grzegorzchmaj.easydiet.models.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import pl.grzegorzchmaj.easydiet.exceptions.ShoppingServiceException;
import pl.grzegorzchmaj.easydiet.models.entities.*;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor

public class ShoppingListService {

    UserInfoService userInfoService;
    DietMealsService dietMealsService;
    UserRepository userRepository;

    @Autowired
    public ShoppingListService(UserInfoService userInfoService, DietMealsService dietMealsService,UserRepository userRepository) {
        this.userInfoService = userInfoService;
        this.dietMealsService = dietMealsService;
        this.userRepository = userRepository;
    }


    public Map<Ingredient,Long> createShoppingList() throws ShoppingServiceException {
        User user = findUser(userInfoService.getUser()).orElseThrow(() -> new ShoppingServiceException("User not found"));
        List<MealInfo> meals = checkDiet(user).getMeals();
        dietMealsService.adjustMealsIngredients(meals);
        Map<Ingredient, Long> shoppingList = new HashMap<>();
        processMeals(meals, shoppingList);
        return shoppingList;
    }

    private Optional<User> findUser(User user) {
        if (user != null) {
            return userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());
        }
        else {
            return Optional.empty();
        }
    }

    private Diet checkDiet(User user){
        try{
            user.getDiet().getMeals();
        } catch(NullPointerException e){
            throw new ShoppingServiceException("User doesn't have diet");
        }
        return user.getDiet();
    }

    private void processMeals(List<MealInfo> meals, Map<Ingredient, Long> shoppingList) {
        for (MealInfo mealInfo : meals) {
            processIngredientWeights(mealInfo.getMeal().getIngredientWeights(), shoppingList);
        }
    }

    private void processIngredientWeights(List<IngredientWeight> ingredientWeights, Map<Ingredient, Long> shoppingList) {
        for (IngredientWeight ingredientWeight: ingredientWeights) {
            processIngredientWeight(ingredientWeight, shoppingList);
        }
    }

    private void processIngredientWeight(IngredientWeight ingredientWeight, Map<Ingredient, Long> shoppingList) {
        Ingredient ingredient = ingredientWeight.getIngredient();
        Long weight = shoppingList.getOrDefault(ingredient, 0L);
        weight += ingredientWeight.getWeight();
        shoppingList.put(ingredient, weight);
    }

}
