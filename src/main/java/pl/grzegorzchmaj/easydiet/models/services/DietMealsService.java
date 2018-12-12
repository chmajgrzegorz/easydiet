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

import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Data
@NoArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DietMealsService {

    private List<MealInfo> meals = new ArrayList<>();

    MealRepository mealRepository;
    DietRepository dietRepository;
    UserRepository userRepository;
    UserInfoService userInfoService;
    MealInfoRepository mealInfoRepository;

    @Autowired
    public DietMealsService(MealRepository mealRepository, DietRepository dietRepository, UserRepository userRepository,
                            UserInfoService userInfoService, MealInfoRepository mealInfoRepository) {
        this.mealRepository = mealRepository;
        this.dietRepository = dietRepository;
        this.userRepository = userRepository;
        this.userInfoService = userInfoService;
        this.mealInfoRepository = mealInfoRepository;
    }

    public void setMealsToDiet(Diet diet) {

        Long daysOfDiet = DAYS.between(diet.getStartDate(), diet.getEndDate()) + 1;
        for (int dayOfDiet = 0; dayOfDiet < daysOfDiet; dayOfDiet++) {
            addMealInfoToDiet(diet, dayOfDiet);
        }
        diet.setMeals(meals);
        dietRepository.save(diet);
        saveDietToUser(diet);
        meals.clear();
    }

    public void addMealInfoToDiet(Diet diet, int dayOfDiet) {
        int numberOfMealsInDay = userInfoService.getUser().getHowManyMeals().getNumberOfMeals();
        for (int mealNumber = 1; mealNumber <= numberOfMealsInDay ; mealNumber++) {
            MealInfo meal = new MealInfo(diet.getStartDate().plusDays(dayOfDiet), "Posiłek " + mealNumber, adjustMeal(numberOfMealsInDay, mealNumber));
            if (isMealEqualToMealInfo(meal)) {
                mealNumber--;
            } else {
                meals.add(meal);
                mealInfoRepository.save(meals.get(meals.size() - 1));
            }
        }
    }

    public boolean isMealEqualToMealInfo(MealInfo meal) {

        return meals.stream()
                .anyMatch(mealInfo -> mealInfo.getMeal().getId() == meal.getMeal().getId());
    }


    public Meal adjustMeal(int numberOfMealsInDay, int mealNumber) {
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
                        return mealRepository.findRandomBreakfast();
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

    public List<MealInfo> adjustIngredients(List<MealInfo> meals) {
        User user = userInfoService.getUser();
        for (MealInfo meal : meals) {
            switch (meal.getName()) {
                case "Posiłek 1":
                    meal.getMeal().getIngredients().forEach(ingredient -> ingredient.setWeight((long) (user.getHowManyMeals().getCaloriesPercentage1() * user.getCalories() / meal.getMeal().getCalories() * ingredient.getWeight())));
                    meal.getMeal().setCalories((int)(meal.getMeal().getCalories() * user.getHowManyMeals().getCaloriesPercentage1() * user.getCalories() / meal.getMeal().getCalories()));
                    break;
                case "Posiłek 2":
                    meal.getMeal().getIngredients().forEach(s -> s.setWeight((long) (user.getHowManyMeals().getCaloriesPercentage2() * user.getCalories() / meal.getMeal().getCalories() * s.getWeight())));
                    meal.getMeal().setCalories((int) (meal.getMeal().getCalories() * user.getHowManyMeals().getCaloriesPercentage2() * user.getCalories() / meal.getMeal().getCalories()));
                    break;
                case "Posiłek 3":
                    meal.getMeal().getIngredients().forEach(s -> s.setWeight((long) (user.getHowManyMeals().getCaloriesPercentage3() * user.getCalories() / meal.getMeal().getCalories() * s.getWeight())));
                    meal.getMeal().setCalories((int) (meal.getMeal().getCalories() * user.getHowManyMeals().getCaloriesPercentage3() * user.getCalories() / meal.getMeal().getCalories()));
                    break;
                case "Posiłek 4":
                    meal.getMeal().getIngredients().forEach(s -> s.setWeight((long) (user.getHowManyMeals().getCaloriesPercentage4() * user.getCalories() / meal.getMeal().getCalories() * s.getWeight())));
                    meal.getMeal().setCalories((int) (meal.getMeal().getCalories() * user.getHowManyMeals().getCaloriesPercentage4() * user.getCalories() / meal.getMeal().getCalories()));
                    break;
                case "Posiłek 5":
                    meal.getMeal().getIngredients().forEach(s -> s.setWeight((long) (user.getHowManyMeals().getCaloriesPercentage5() * user.getCalories() / meal.getMeal().getCalories() * s.getWeight())));
                    meal.getMeal().setCalories((int) (meal.getMeal().getCalories() * user.getHowManyMeals().getCaloriesPercentage5() * user.getCalories() / meal.getMeal().getCalories()));
                    break;
                default:
            }
        }
        return meals;
    }

    public void removePreviousDietIfPresent(){
        Optional<Diet> previousDiet = dietRepository.findByUserId(userInfoService.getUser().getId());
        if(previousDiet.isPresent()){
            dietRepository.deleteById(previousDiet.get().getId());
        }
    }

    public void saveDietToUser(Diet diet) {
        User user = userRepository.findByLoginAndPassword(userInfoService.getUser().getLogin(), userInfoService.getUser().getPassword()).get();
        user.setDiet(diet);
        userRepository.save(user);
    }


}
