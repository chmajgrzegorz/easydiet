package pl.grzegorzchmaj.easydiet.models.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import pl.grzegorzchmaj.easydiet.enums.HowManyMeals;
import pl.grzegorzchmaj.easydiet.models.entities.Diet;
import pl.grzegorzchmaj.easydiet.models.entities.Meal;
import pl.grzegorzchmaj.easydiet.models.entities.MealInfo;
import pl.grzegorzchmaj.easydiet.models.entities.User;
import pl.grzegorzchmaj.easydiet.repositories.DietRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealInfoRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealRepository;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

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

    public void setMealsToDiet(Diet diet){
        User user= userInfoService.getUser();
        Long days = DAYS.between(diet.getStartDate(), diet.getEndDate())+1;
        for (int i = 0; i < days; i++) {
            for(int j = 1 ; j<diet.getUser().getMeals().getHowMany() ; j++){
                meals.add(new MealInfo(diet.getStartDate().plusDays(i),"Posiłek " + j, adjustMeal(user.getMeals(), j)));
                mealInfoRepository.save(meals.get(meals.size()-1));
            }
        }
        diet.setMeals(meals);
        dietRepository.save(diet);
        saveDietToUser(diet);
    }

    public Meal adjustMeal(HowManyMeals howManyMeals, int numberOfMeal){
        switch(numberOfMeal){
            case 1:
                return adjustCalories(mealRepository.findRandomBreakfast(),howManyMeals, numberOfMeal);
            case 2:
                switch(howManyMeals.getHowMany()){
                    case 3:
                        return adjustCalories(mealRepository.findRandomDinner(),howManyMeals, numberOfMeal);
                    default:
                        return adjustCalories(mealRepository.findRandomMeal(),howManyMeals, numberOfMeal);
                }
            case 3:
                switch(howManyMeals.getHowMany()){
                    case 3:
                        return adjustCalories(mealRepository.findRandomMeal(),howManyMeals, numberOfMeal);
                    default:
                        return adjustCalories(mealRepository.findRandomDinner(),howManyMeals, numberOfMeal);
                }
            case 4:
                return adjustCalories(mealRepository.findRandomMeal(),howManyMeals, numberOfMeal);
            case 5:
                return adjustCalories(mealRepository.findRandomMeal(),howManyMeals, numberOfMeal);
            default:
                return new Meal();
        }
    }

    public Meal adjustCalories(Meal previousMeal, HowManyMeals howManyMeals, int numberOfMeal){
        Meal meal = new Meal();
        meal = previousMeal;
        int calories = userInfoService.getUser().getCalories();
        switch(numberOfMeal){
            case 1:
                meal.getIngredients().forEach(s -> s.setWeight(howManyMeals.getCaloriesPercentage1()*calories/s.getWeight()));
                return meal;
            case 2:
                meal.getIngredients().forEach(s -> s.setWeight(howManyMeals.getCaloriesPercentage2()*calories/s.getWeight()));
                return meal;
            case 3:
                meal.getIngredients().forEach(s -> s.setWeight(howManyMeals.getCaloriesPercentage3()*calories/s.getWeight()));
                return meal;
            case 4:
                meal.getIngredients().forEach(s -> s.setWeight(howManyMeals.getCaloriesPercentage4()*calories/s.getWeight()));
                return meal;
            case 5:
                meal.getIngredients().forEach(s -> s.setWeight(howManyMeals.getCaloriesPercentage5()*calories/s.getWeight()));
                return meal;
            default:
                return new Meal();
        }

    }

    public void saveDietToUser(Diet diet){
        User user = userRepository.findByLoginAndPassword(userInfoService.getUser().getLogin(), userInfoService.getUser().getPassword()).get();
        user.setDiet(diet);
        userRepository.save(user);
    }



}
