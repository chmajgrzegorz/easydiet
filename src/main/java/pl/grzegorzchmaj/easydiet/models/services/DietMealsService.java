package pl.grzegorzchmaj.easydiet.models.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import pl.grzegorzchmaj.easydiet.enums.HowManyMeals;
import pl.grzegorzchmaj.easydiet.models.entities.*;
import pl.grzegorzchmaj.easydiet.repositories.DietRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealInfoRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealRepository;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Data
@NoArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DietMealsService {

    MealRepository mealRepository;
    DietRepository dietRepository;
    UserRepository userRepository;
    MealInfoRepository mealInfoRepository;

    @Autowired
    public DietMealsService(MealRepository mealRepository, DietRepository dietRepository, UserRepository userRepository,
                            MealInfoRepository mealInfoRepository) {
        this.mealRepository = mealRepository;
        this.dietRepository = dietRepository;
        this.userRepository = userRepository;
        this.mealInfoRepository = mealInfoRepository;
    }

    public void setMealsToDiet(Diet diet, User user) {
        List<MealInfo> meals = new ArrayList<>();
        Long daysOfDiet = DAYS.between(diet.getStartDate(), diet.getEndDate()) + 1;
        addMealInfosToDiet(diet, meals, daysOfDiet, user.getHowManyMeals().getNumberOfMeals());
        diet.setMeals(meals);
        dietRepository.save(diet);
        saveDietToUser(diet, user);
    }

    private void addMealInfosToDiet(Diet diet, List<MealInfo> meals, Long daysOfDiet, int numberOfMealsInDay) {
        for (int dayOfDiet = 0; dayOfDiet < daysOfDiet; dayOfDiet++) {
            addMealInfoToDiet(diet, dayOfDiet, meals, numberOfMealsInDay);
        }
    }

    private void addMealInfoToDiet(Diet diet, int dayOfDiet, List<MealInfo> meals, int numberOfMealsInDay) {
        for (int mealNumber = 1; mealNumber <= numberOfMealsInDay ; mealNumber++) {
            MealInfo meal = new MealInfo(diet.getStartDate().plusDays(dayOfDiet), "Posiłek " + mealNumber, findMeal(numberOfMealsInDay, mealNumber));
            if (isMealEqualToMealInfo(meal,meals)) {
                mealNumber--;
            } else {
                meals.add(meal);
                mealInfoRepository.save(meals.get(meals.size() - 1));
            }
        }
    }

    private boolean isMealEqualToMealInfo(MealInfo meal, List<MealInfo> meals) {

        return meals.stream()
                .anyMatch(mealInfo -> mealInfo.getMeal().getId().equals(meal.getMeal().getId()));
    }


    private Meal findMeal(int numberOfMealsInDay, int mealNumber) {
        switch (mealNumber) {
            case 1:
                return mealRepository.findRandomBreakfast();
            case 2:
                switch (numberOfMealsInDay) {
                    case 3:
                        return mealRepository.findRandomDinner();
                    default:
                        return mealRepository.findRandomLunch();
                }
            case 3:
                switch (numberOfMealsInDay) {
                    case 3:
                        return mealRepository.findRandomSupper();
                    default:
                        return mealRepository.findRandomDinner();
                }
            case 4:
                switch (numberOfMealsInDay) {
                    case 4:
                        return mealRepository.findRandomSupper();
                    default:
                        return mealRepository.findRandomTea();
                }
            case 5:
                return mealRepository.findRandomSupper();
            default:
                return new Meal();
        }
    }

    public void adjustMealsIngredients(List<MealInfo> meals, User user) {
        for (MealInfo meal : meals) {
            switch (meal.getName()) {
                case "Posiłek 1":
                    adjustIngredientsCalories(user, meal, user.getHowManyMeals().getCaloriesPercentage1());
                    break;
                case "Posiłek 2":
                    adjustIngredientsCalories(user, meal, user.getHowManyMeals().getCaloriesPercentage2());
                    break;
                case "Posiłek 3":
                    adjustIngredientsCalories(user, meal, user.getHowManyMeals().getCaloriesPercentage3());
                    break;
                case "Posiłek 4":
                    adjustIngredientsCalories(user, meal, user.getHowManyMeals().getCaloriesPercentage4());
                    break;
                case "Posiłek 5":
                    adjustIngredientsCalories(user, meal, user.getHowManyMeals().getCaloriesPercentage5());
                    break;
                default:
            }
        }
    }

    private void adjustIngredientsCalories(User user, MealInfo meal, double caloriesPercentage) {
        meal.getMeal().getIngredientWeights().forEach(ingredient -> ingredient.setWeight((long) (caloriesPercentage * user.getCalories() / meal.getMeal().getCalories() * ingredient.getWeight())));
        meal.getMeal().setCalories((int) (meal.getMeal().getCalories() * caloriesPercentage * user.getCalories() / meal.getMeal().getCalories()));
    }


    public void removePreviousDietIfPresent(User user){
        Optional<Diet> previousDiet = dietRepository.findByUserId(user.getId());
        previousDiet.ifPresent(diet -> dietRepository.deleteById(diet.getId()));
    }

    private void saveDietToUser(Diet diet, User user) {
        user.setDiet(diet);
        userRepository.save(user);
    }


}
