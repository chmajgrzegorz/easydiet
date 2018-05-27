package pl.grzegorzchmaj.easydiet.models.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import pl.grzegorzchmaj.easydiet.models.entities.Ingredient;
import pl.grzegorzchmaj.easydiet.models.entities.MealInfo;
import pl.grzegorzchmaj.easydiet.models.entities.User;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor
@Transactional
public class ShoppingListService {

    Map<Ingredient, Long> shoppingList = new HashMap<>();
    List<MealInfo> meals = new ArrayList<>();
    UserInfoService userInfoService;
    DietMealsService dietMealsService;
    UserRepository userRepository;
    User user;

    @Autowired
    public ShoppingListService(UserInfoService userInfoService, DietMealsService dietMealsService,UserRepository userRepository) {
        this.userInfoService = userInfoService;
        this.dietMealsService = dietMealsService;
        this.userRepository = userRepository;
    }

    public Map<Ingredient,Long> createShoppingList(){
        user = userRepository.findByLoginAndPassword(userInfoService.getUser().getLogin(),userInfoService.getUser().getPassword()).get();
        shoppingList.clear();
        meals.clear();
        System.out.println("Wyczyszczona mapa: " + shoppingList);
        meals = user.getDiet().getMeals();
        System.out.println("Meals: " + meals);
        meals=dietMealsService.adjustIngredients(meals);
        for (MealInfo meal : meals) {
            System.out.println("Meal: " + meal.getName());
            meal.getMeal().getIngredients().forEach(s -> {
                if(shoppingList.containsKey(s.getIngredient()))
                    shoppingList.put(s.getIngredient(), s.getWeight()+shoppingList.get(s.getIngredient()));
                else
                shoppingList.put(s.getIngredient(),s.getWeight());
            });
        }
        return shoppingList;
    }
}
