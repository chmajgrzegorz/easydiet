package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.models.entities.IngredientWeight;
import pl.grzegorzchmaj.easydiet.models.entities.Meal;
import pl.grzegorzchmaj.easydiet.models.forms.MealForm;
import pl.grzegorzchmaj.easydiet.models.services.MealIngredientsService;
import pl.grzegorzchmaj.easydiet.models.services.MealsListService;
import pl.grzegorzchmaj.easydiet.models.services.UserInfoService;
import pl.grzegorzchmaj.easydiet.repositories.CategoryRepository;
import pl.grzegorzchmaj.easydiet.repositories.IngredientRepository;
import pl.grzegorzchmaj.easydiet.repositories.IngredientWeightRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MealController {


    UserInfoService userInfoService;
    MealsListService mealsListService;
    MealRepository mealRepository;
    IngredientRepository ingredientRepository;
    CategoryRepository categoryRepository;
    IngredientWeightRepository ingredientWeightRepository;
    MealIngredientsService mealIngredientsService;

    @Autowired
    public MealController(CategoryRepository categoryRepository, UserInfoService userInfoService, IngredientWeightRepository ingredientWeightRepository,
                          MealsListService mealsListService, MealRepository mealRepository, IngredientRepository ingredientRepository,
                          MealIngredientsService mealIngredientsService) {
        this.userInfoService = userInfoService;
        this.mealsListService = mealsListService;
        this.mealRepository = mealRepository;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientWeightRepository = ingredientWeightRepository;
        this.mealIngredientsService = mealIngredientsService;
    }



    @GetMapping("/mealweight")
    public String addMealWeight(Model model, RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        IngredientWeight ingredientWeight = new IngredientWeight();
        ingredientWeight.setIngredient(mealIngredientsService.getOneIngredient());
        model.addAttribute("model", ingredientWeight);
        model.addAttribute("name", ingredientWeight.getIngredient().getName());
        return "mealweight";
    }


    @PostMapping("/mealweight")
    public String addMealWeightPost(RedirectAttributes attr, @ModelAttribute("model") IngredientWeight ingredientWeight){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        ingredientWeightRepository.save(ingredientWeight);
        mealIngredientsService.addIngredientWeight(ingredientWeight);
        if(mealIngredientsService.ingredientsIsNull()){
            Meal meal = new Meal(mealIngredientsService.getMealForm());
            meal.setIngredients(mealIngredientsService.getSetIngredients());
            mealRepository.save(meal);
            mealIngredientsService.clear();
            attr.addFlashAttribute("info", "Dodano posiłek");
            return "redirect:/home";
        }
        return "redirect:/mealweight";
    }


    @GetMapping("/meals")
    public String showMeals(RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }

        return "meals";
    }

    @GetMapping("/addmeal")
    public String addMeal(Model model, RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        model.addAttribute("meal", new MealForm());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "meal";
    }

    @PostMapping("/addmeal")
    public String addMealPost(@ModelAttribute("meal") MealForm mealForm){
        mealIngredientsService.setIngredients(mealForm.getIngredients());
        mealIngredientsService.setName(mealForm.getName());
        mealIngredientsService.setMealForm(mealForm);
        return "redirect:/mealweight";
    }
}
