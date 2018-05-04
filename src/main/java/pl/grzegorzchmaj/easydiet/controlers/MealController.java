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
import pl.grzegorzchmaj.easydiet.models.services.TextToHtml;
import pl.grzegorzchmaj.easydiet.models.services.UserInfoService;
import pl.grzegorzchmaj.easydiet.repositories.CategoryRepository;
import pl.grzegorzchmaj.easydiet.repositories.IngredientRepository;
import pl.grzegorzchmaj.easydiet.repositories.IngredientWeightRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealRepository;

import java.util.stream.Collectors;


@Controller
public class MealController {

    UserInfoService userInfoService;
    MealRepository mealRepository;
    IngredientRepository ingredientRepository;
    CategoryRepository categoryRepository;
    IngredientWeightRepository ingredientWeightRepository;
    MealIngredientsService mealIngredientsService;
    TextToHtml textToHtml;

    @Autowired
    public MealController(UserInfoService userInfoService,MealRepository mealRepository,
                          IngredientRepository ingredientRepository, CategoryRepository categoryRepository,
                          IngredientWeightRepository ingredientWeightRepository, MealIngredientsService mealIngredientsService,
                          TextToHtml textToHtml) {
        this.userInfoService = userInfoService;
        this.mealRepository = mealRepository;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientWeightRepository = ingredientWeightRepository;
        this.mealIngredientsService = mealIngredientsService;
        this.textToHtml = textToHtml;
    }

    @GetMapping("/meals")
    public String showMeals(Model model, RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        model.addAttribute("meals", mealRepository.findAll());
        return "meals";
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
    public String addMealPost(@ModelAttribute("meal") MealForm mealForm, RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        mealForm.setDescription(textToHtml.toHTML(mealForm.getDescription()));
        mealIngredientsService.setIngredients(mealForm.getIngredients());
        mealIngredientsService.setName(mealForm.getName());
        mealIngredientsService.setMealForm(mealForm);
        return "redirect:/mealweight";
    }


}
