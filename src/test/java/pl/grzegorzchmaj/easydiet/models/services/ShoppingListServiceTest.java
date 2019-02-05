//package pl.grzegorzchmaj.easydiet.models.services;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.mockito.junit.MockitoJUnitRunner;
//import pl.grzegorzchmaj.easydiet.enums.HowManyMeals;
//import pl.grzegorzchmaj.easydiet.enums.PhysicalActivity;
//import pl.grzegorzchmaj.easydiet.enums.Plans;
//import pl.grzegorzchmaj.easydiet.enums.Sex;
//import pl.grzegorzchmaj.easydiet.exceptions.ShoppingServiceException;
//import pl.grzegorzchmaj.easydiet.models.entities.*;
//import pl.grzegorzchmaj.easydiet.repositories.UserRepository;
//
//import java.util.*;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ShoppingListServiceTest {
//
//    @InjectMocks
//    ShoppingListService shoppingListService;
//
//    @Mock
//    UserInfoService userInfoService;
//
//    @Mock
//    DietMealsService dietMealsService;
//
//    @Mock
//    UserRepository userRepository;
//
//    private Ingredient pineapple;
//    private Ingredient bread;
//    private Ingredient butter;
//    private IngredientWeight pineappleWeight;
//    private IngredientWeight bread1Weight;
//    private IngredientWeight bread2Weight;
//    private IngredientWeight butterWeight;
//    private Meal meal1;
//    private Meal meal2;
//    private Meal meal3;
//    private MealInfo mealInfo1;
//    private MealInfo mealInfo2;
//    private MealInfo mealInfo3;
//    private Diet diet;
//    private User user;
//    private User user2;
//
//    @Before
//    public void setUp() {
//
//        //Ingredient
//        pineapple = new Ingredient();
//        pineapple.setName("Pineapple");
//        bread = new Ingredient();
//        bread.setName("Bread");
//        butter = new Ingredient();
//        butter.setName("Butter");
//
//        //IngredientWeight
//        pineappleWeight = new IngredientWeight();
//        pineappleWeight.setIngredient(pineapple);
//        pineappleWeight.setWeight(200L);
//        bread1Weight = new IngredientWeight();
//        bread1Weight.setIngredient(bread);
//        bread1Weight.setWeight(300L);
//        bread2Weight = new IngredientWeight();
//        bread2Weight.setIngredient(bread);
//        bread2Weight.setWeight(200L);
//        butterWeight = new IngredientWeight();
//        butterWeight.setIngredient(butter);
//        butterWeight.setWeight(50L);
//
//        //Meal
//        meal1 = new Meal();
//        meal1.setIngredientWeights(Arrays.asList(bread1Weight,butterWeight));
//        meal2 = new Meal();
//        meal2.setIngredientWeights(Arrays.asList(pineappleWeight,bread2Weight));
//        meal3 = new Meal();
//        meal3.setIngredientWeights(Arrays.asList(butterWeight,bread2Weight));
//
//        //MealInfo
//        mealInfo1 = new MealInfo();
//        mealInfo1.setMeal(meal1);
//        mealInfo1.setName("Posiłek 1"); //Meal 1
//        mealInfo2 = new MealInfo();
//        mealInfo2.setMeal(meal2);
//        mealInfo2.setName("Posiłek 2"); //Meal 2
//        mealInfo3 = new MealInfo();
//        mealInfo3.setMeal(meal3);
//        mealInfo3.setName("Posiłek 3"); //Meal 3
//
//        //Diet
//        diet = new Diet();
//        diet.setMeals(Arrays.asList(mealInfo1,mealInfo2,mealInfo3));
//
//        //User
//        user = new User();
//        user.setDiet(diet);
//        user.setLogin("123");
//        user.setPassword("123");
//        user2 = new User();
//        user2.setLogin("123");
//        user2.setPassword("123");
//
//    }
//
//    @Test(expected = ShoppingServiceException.class)
//    public void shouldThrownShoppingServiceExceptionWhenUserNotFound() throws ShoppingServiceException {
//
//        shoppingListService.createShoppingList();
//    }
//
//    @Test
//    public void shouldReturnShoppingListWhenUserHasDiet(){
//
//        when(userInfoService.getUser()).thenReturn(user);
//        when(userRepository.findByLoginAndPassword(anyString(),anyString())).thenReturn(Optional.of(user));
//        doNothing().when(dietMealsService).adjustMealsIngredients(anyList());
//        Map<Ingredient,Long> expectedResult = new HashMap<>();
//        expectedResult.put(pineapple, 200L);
//        expectedResult.put(bread, 700L);
//        expectedResult.put(butter,100L);
//        Map<Ingredient,Long> actualResult = shoppingListService.createShoppingList();
//        assertEquals(actualResult,expectedResult);
//
//    }
//
//    @Test(expected = ShoppingServiceException.class)
//    public void shouldReturnShoppingServiceExceptionWhenUserDoesntHaveDiet(){
//
//        when(userInfoService.getUser()).thenReturn(user2);
//        when(userRepository.findByLoginAndPassword(anyString(),anyString())).thenReturn(Optional.of(user2));
//        Map<Ingredient,Long> expectedResult = new HashMap<>();
//        Map<Ingredient,Long> actualResult = shoppingListService.createShoppingList();
//        assertEquals(actualResult,expectedResult);
//
//    }
//}