package pl.grzegorzchmaj.easydiet.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.grzegorzchmaj.easydiet.exceptions.ShoppingServiceException;
import pl.grzegorzchmaj.easydiet.models.ShoppingList;
import pl.grzegorzchmaj.easydiet.entities.*;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingListServiceTest {

    @InjectMocks
    private ShoppingListService shoppingListService;

    @Mock
    private DietMealsService dietMealsService;
    @Mock
    private User user;
    @Mock
    private Diet diet;
    @Mock
    private MealInfo mealInfo;


    @Test(expected = ShoppingServiceException.class)
    public void testCreateShoppingListUserDietNull() throws ShoppingServiceException {
        //SETUP
        User user = mock(User.class);
        when(user.getDiet()).thenReturn(null);

        //CALL
        shoppingListService.createShoppingList(user);
    }

    @Test(expected = ShoppingServiceException.class)
    public void testCreateShoppingListUserDietMealsNull(){
        //SETUP
        when(user.getDiet()).thenReturn(diet);
        when(diet.getMeals()).thenReturn(null);

        //CALL
        shoppingListService.createShoppingList(user);

    }

    @Test(expected = ShoppingServiceException.class)
    public void testCreateShoppingListUserDietMealsEmpty() {
        // SETUP
        when(user.getDiet()).thenReturn(diet);
        List<MealInfo> mealsInfo = new ArrayList<>();
        when(diet.getMeals()).thenReturn(mealsInfo);

        // CALL
        shoppingListService.createShoppingList(user);
    }

    @Test
    public void testCreateShoppingListAddsWeights() {
        // SETUP
        when(user.getDiet()).thenReturn(diet);
        when(diet.getMeals()).thenReturn(Collections.singletonList(mealInfo));
        Meal meal = mock(Meal.class);
        when(mealInfo.getMeal()).thenReturn(meal);
        IngredientWeight ingredientWeight1 = mock(IngredientWeight.class);
        IngredientWeight ingredientWeight2 = mock(IngredientWeight.class);
        when(meal.getIngredientWeights()).thenReturn(Arrays.asList(ingredientWeight1, ingredientWeight2));
        Ingredient ingredient = mock(Ingredient.class);
        when(ingredientWeight1.getIngredient()).thenReturn(ingredient);
        when(ingredientWeight2.getIngredient()).thenReturn(ingredient);
        Long weight1 = 42L;
        Long weight2 = 1337L;
        when(ingredientWeight1.getWeight()).thenReturn(weight1);
        when(ingredientWeight2.getWeight()).thenReturn(weight2);

        // CALL
        ShoppingList shoppingList = shoppingListService.createShoppingList(user);

        // VERIFY
        Long expectedWeight = weight1 + weight2;
        Long actualWeight = shoppingList.getIngredientWeights().get(ingredient);
        assertEquals(expectedWeight, actualWeight);
    }

}