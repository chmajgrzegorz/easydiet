package pl.grzegorzchmaj.easydiet.models.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.grzegorzchmaj.easydiet.enums.HowManyMeals;
import pl.grzegorzchmaj.easydiet.models.entities.*;
import pl.grzegorzchmaj.easydiet.repositories.DietRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealInfoRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealRepository;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DietMealsServiceTest {

    @InjectMocks
    DietMealsService dietMealsService;

    @Mock
    UserRepository userRepository;
    @Mock
    MealRepository mealRepository;
    @Mock
    DietRepository dietRepository;
    @Mock
    MealInfoRepository mealInfoRepository;
    @Mock
    private User user;
    @Mock
    private Diet diet;


    @Before
    public void setUp() {

        //Diet
        diet = new Diet();
        diet.setStartDate(LocalDate.of(2018,8,11));
        diet.setEndDate(LocalDate.of(2018,8,11));

    }

    @Test
    public void testDietMealsServiceThreeMeals() {

        //SETUP
        Meal breakfast = mock(Meal.class);
        Meal dinner = mock(Meal.class);
        Meal supper = mock(Meal.class);
        when(user.getHowManyMeals()).thenReturn(HowManyMeals.THREE);
        when(breakfast.getId()).thenReturn(1L);
        when(dinner.getId()).thenReturn(2L);
        when(supper.getId()).thenReturn(3L);
        when(mealRepository.findRandomBreakfast()).thenReturn(breakfast);
        when(mealRepository.findRandomDinner()).thenReturn(dinner);
        when(mealRepository.findRandomSupper()).thenReturn(supper);

        //CALL
        dietMealsService.setMealsToDiet(diet, user);

        //VERIFY
        ArgumentCaptor<Diet> dietCaptor = ArgumentCaptor.forClass(Diet.class);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<MealInfo> mealInfoCaptor = ArgumentCaptor.forClass(MealInfo.class);

        verify(dietRepository, times(1)).save(dietCaptor.capture());
        verify(userRepository,times(1)).save(userCaptor.capture());
        verify(mealInfoRepository,times(3)).save(mealInfoCaptor.capture());
        verify(mealRepository,times(1)).findRandomBreakfast();
        verify(mealRepository,times(1)).findRandomSupper();
        verify(mealRepository,times(1)).findRandomDinner();

        final Diet actualDiet =  dietCaptor.getValue();
        assertEquals(actualDiet.getMeals().get(0).getMeal(),breakfast);
        assertEquals(actualDiet.getMeals().get(1).getMeal(),dinner);
        assertEquals(actualDiet.getMeals().get(2).getMeal(),supper);

    }

    @Test
    public void testDietMealsServiceFourMeals() {

        //SETUP
        Meal breakfast = mock(Meal.class);
        Meal dinner = mock(Meal.class);
        Meal supper = mock(Meal.class);
        Meal lunch = mock(Meal.class);
        when(breakfast.getId()).thenReturn(1L);
        when(dinner.getId()).thenReturn(2L);
        when(supper.getId()).thenReturn(3L);
        when(lunch.getId()).thenReturn(4L);
        when(user.getHowManyMeals()).thenReturn(HowManyMeals.FOUR);
        when(mealRepository.findRandomBreakfast()).thenReturn(breakfast);
        when(mealRepository.findRandomDinner()).thenReturn(dinner);
        when(mealRepository.findRandomSupper()).thenReturn(supper);
        when(mealRepository.findRandomLunch()).thenReturn(lunch);

        //CALL
        dietMealsService.setMealsToDiet(diet, user);

        //VERIFY
        ArgumentCaptor<Diet> dietCaptor = ArgumentCaptor.forClass(Diet.class);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<MealInfo> mealInfoCaptor = ArgumentCaptor.forClass(MealInfo.class);

        verify(dietRepository, times(1)).save(dietCaptor.capture());
        verify(userRepository,times(1)).save(userCaptor.capture());
        verify(mealInfoRepository,times(4)).save(mealInfoCaptor.capture());
        verify(mealRepository,times(1)).findRandomBreakfast();
        verify(mealRepository,times(1)).findRandomSupper();
        verify(mealRepository,times(1)).findRandomLunch();
        verify(mealRepository,times(1)).findRandomDinner();

        final Diet actualDiet =  dietCaptor.getValue();
        assertEquals(actualDiet.getMeals().get(0).getMeal(),breakfast);
        assertEquals(actualDiet.getMeals().get(1).getMeal(),lunch);
        assertEquals(actualDiet.getMeals().get(2).getMeal(),dinner);
        assertEquals(actualDiet.getMeals().get(3).getMeal(),supper);

    }

    @Test
    public void testDietMealsServiceFiveMeals() {

        //SETUP
        Meal breakfast = mock(Meal.class);
        Meal dinner = mock(Meal.class);
        Meal supper = mock(Meal.class);
        Meal lunch = mock(Meal.class);
        Meal tea = mock(Meal.class);
        when(breakfast.getId()).thenReturn(1L);
        when(dinner.getId()).thenReturn(2L);
        when(supper.getId()).thenReturn(3L);
        when(lunch.getId()).thenReturn(4L);
        when(tea.getId()).thenReturn(5L);
        when(user.getHowManyMeals()).thenReturn(HowManyMeals.FIVE);
        when(mealRepository.findRandomBreakfast()).thenReturn(breakfast);
        when(mealRepository.findRandomLunch()).thenReturn(lunch);
        when(mealRepository.findRandomDinner()).thenReturn(dinner);
        when(mealRepository.findRandomSupper()).thenReturn(supper);
        when(mealRepository.findRandomTea()).thenReturn(tea);

        //CALL
        dietMealsService.setMealsToDiet(diet, user);

        //VERIFY
        ArgumentCaptor<Diet> dietCaptor = ArgumentCaptor.forClass(Diet.class);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<MealInfo> mealInfoCaptor = ArgumentCaptor.forClass(MealInfo.class);

        verify(dietRepository, times(1)).save(dietCaptor.capture());
        verify(userRepository,times(1)).save(userCaptor.capture());
        verify(mealInfoRepository,times(5)).save(mealInfoCaptor.capture());
        verify(mealRepository,times(1)).findRandomBreakfast();
        verify(mealRepository,times(1)).findRandomSupper();
        verify(mealRepository,times(1)).findRandomLunch();
        verify(mealRepository,times(1)).findRandomDinner();
        verify(mealRepository,times(1)).findRandomTea();

        final Diet actualDiet =  dietCaptor.getValue();
        assertEquals(actualDiet.getMeals().get(0).getMeal(),breakfast);
        assertEquals(actualDiet.getMeals().get(1).getMeal(),lunch);
        assertEquals(actualDiet.getMeals().get(2).getMeal(),dinner);
        assertEquals(actualDiet.getMeals().get(3).getMeal(),tea);
        assertEquals(actualDiet.getMeals().get(4).getMeal(),supper);
    }


    @Test
    public void testRemovePreviousDietIfPresent() {

        //SETUP
        when(dietRepository.findByUserId(anyLong())).thenReturn(Optional.of(diet));

        //CALL
        dietMealsService.removePreviousDietIfPresent(user);

        //VERIFY
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(dietRepository,times(1)).deleteById(longArgumentCaptor.capture());

    }

    @Test
    public void testRemovePreviousDietIfNotPresent() {

        //SETUP
        when(dietRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

        //CALL
        dietMealsService.removePreviousDietIfPresent(user);

        //VERIFY
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(dietRepository,times(1)).findByUserId(longArgumentCaptor.capture());
        Mockito.verifyZeroInteractions(dietRepository);

    }
}