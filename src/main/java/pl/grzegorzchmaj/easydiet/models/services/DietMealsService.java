package pl.grzegorzchmaj.easydiet.models.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import pl.grzegorzchmaj.easydiet.enums.HowManyMeals;
import pl.grzegorzchmaj.easydiet.models.entities.*;
import pl.grzegorzchmaj.easydiet.repositories.DietRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealInfoRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealRepository;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

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
        User user = userInfoService.getUser();
        Long days = DAYS.between(diet.getStartDate(), diet.getEndDate())+1;
        MealInfo meal;
        for (int i = 0; i < days; i++) {
            mealFor: for (int j = 1 ; j<=diet.getUser().getMeals().getHowMany() ; j++){
                meal = new MealInfo(diet.getStartDate().plusDays(i),"Posiłek " + j, adjustMeal(user.getMeals(), j));
                for (MealInfo mealInfo : meals) {
                    if(mealInfo.getMeal().getId() == meal.getMeal().getId()){
                        j--;
                        continue mealFor;
                    }
                }
                meals.add(meal);
                mealInfoRepository.save(meals.get(meals.size()-1));
            }
        }
        diet.setMeals(meals);
        dietRepository.save(diet);
        saveDietToUser(diet);
        meals.clear();
    }

    public Meal adjustMeal(HowManyMeals howManyMeals, int numberOfMeal){
            switch (numberOfMeal) {
                case 1:
                    return mealRepository.findRandomBreakfast();
                case 2:
                    switch (howManyMeals.getHowMany()) {
                        case 3:
                            return mealRepository.findRandomDinner();
                        default:
                            return mealRepository.findRandomLunch();
                    }
                case 3:
                    switch (howManyMeals.getHowMany()) {
                        case 3:
                            return mealRepository.findRandomBreakfast();
                        default:
                            return mealRepository.findRandomDinner();
                    }
                case 4:
                    switch (howManyMeals.getHowMany()) {
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

    public List<MealInfo> adjustIngredients(List<MealInfo> meals){
        User user = userInfoService.getUser();
        for (MealInfo meal : meals) {
            switch(meal.getName()){
                case "Posiłek 1":
                    meal.getMeal().getIngredients().forEach(s -> s.setWeight((long)(user.getMeals().getCaloriesPercentage1()*user.getCalories()/meal.getMeal().getCalories()*s.getWeight())));
                    meal.getMeal().setCalories((int)(meal.getMeal().getCalories()*user.getMeals().getCaloriesPercentage1()*user.getCalories()/meal.getMeal().getCalories()));
                    break;
                case "Posiłek 2":
                    meal.getMeal().getIngredients().forEach(s -> s.setWeight((long)(user.getMeals().getCaloriesPercentage2()*user.getCalories()/meal.getMeal().getCalories()*s.getWeight())));
                    meal.getMeal().setCalories((int)(meal.getMeal().getCalories()*user.getMeals().getCaloriesPercentage2()*user.getCalories()/meal.getMeal().getCalories()));
                    break;
                case "Posiłek 3":
                    meal.getMeal().getIngredients().forEach(s -> s.setWeight((long)(user.getMeals().getCaloriesPercentage3()*user.getCalories()/meal.getMeal().getCalories()*s.getWeight())));
                    meal.getMeal().setCalories((int)(meal.getMeal().getCalories()*user.getMeals().getCaloriesPercentage3()*user.getCalories()/meal.getMeal().getCalories()));
                    break;
                case "Posiłek 4":
                    meal.getMeal().getIngredients().forEach(s -> s.setWeight((long)(user.getMeals().getCaloriesPercentage4()*user.getCalories()/meal.getMeal().getCalories()*s.getWeight())));
                    meal.getMeal().setCalories((int)(meal.getMeal().getCalories()*user.getMeals().getCaloriesPercentage4()*user.getCalories()/meal.getMeal().getCalories()));
                    break;
                case "Posiłek 5":
                    meal.getMeal().getIngredients().forEach(s -> s.setWeight((long)(user.getMeals().getCaloriesPercentage5()*user.getCalories()/meal.getMeal().getCalories()*s.getWeight())));
                    meal.getMeal().setCalories((int)(meal.getMeal().getCalories()*user.getMeals().getCaloriesPercentage5()*user.getCalories()/meal.getMeal().getCalories()));
                    break;
                default:
            }
        }
        return meals;
    }

    public void saveDietToUser(Diet diet){
        User user = userRepository.findByLoginAndPassword(userInfoService.getUser().getLogin(), userInfoService.getUser().getPassword()).get();
        user.setDiet(diet);
        userRepository.save(user);
    }



}
