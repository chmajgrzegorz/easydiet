package pl.grzegorzchmaj.easydiet.models.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.grzegorzchmaj.easydiet.models.entities.*;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingListServiceTest {

    @Mock
    User user;

    @Mock
    UserRepository userRepository;

    @Mock
    DietMealsService dietMealsService;

    @Mock
    UserInfoService userInfoService;

    ShoppingListService shoppingListService;

    @Before
    public void setUp() {
        shoppingListService = new ShoppingListService();
    }

    @Test
    public void createShoppingList() {

        //GIVEN
        //        Ingredient pineapple = new Ingredient().builder().name("Pineapple").caloriesPer100g(54F).carbohydratePer100g(13.6F).fatPer100g(0.2F).proteinPer100g(0.8F).build();
        //        Ingredient watermelon = new Ingredient().builder().name("Watermelon").caloriesPer100g(36F).carbohydratePer100g(8.4F).fatPer100g(0.1F).proteinPer100g(0.6F).build();
        //
        //        IngredientWeight pineappleWithWeight...
        //
        //        //after this create Meal, MealInfo, Diet...

    }
}