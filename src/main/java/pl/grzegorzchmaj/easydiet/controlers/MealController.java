package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.entities.IngredientWeight;
import pl.grzegorzchmaj.easydiet.entities.Meal;
import pl.grzegorzchmaj.easydiet.forms.MealForm;
import pl.grzegorzchmaj.easydiet.repositories.CategoryRepository;
import pl.grzegorzchmaj.easydiet.repositories.IngredientRepository;
import pl.grzegorzchmaj.easydiet.repositories.IngredientWeightRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealRepository;
import pl.grzegorzchmaj.easydiet.services.MealIngredientsService;
import pl.grzegorzchmaj.easydiet.services.TextToHtml;


@Controller
public class MealController {

    private MealRepository mealRepository;
    private IngredientRepository ingredientRepository;
    private CategoryRepository categoryRepository;
    private IngredientWeightRepository ingredientWeightRepository;
    private MealIngredientsService mealIngredientsService;
    private TextToHtml textToHtml;

    @Autowired
    public MealController(MealRepository mealRepository,
                          IngredientRepository ingredientRepository, CategoryRepository categoryRepository,
                          IngredientWeightRepository ingredientWeightRepository, MealIngredientsService mealIngredientsService,
                          TextToHtml textToHtml) {
        this.mealRepository = mealRepository;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientWeightRepository = ingredientWeightRepository;
        this.mealIngredientsService = mealIngredientsService;
        this.textToHtml = textToHtml;
    }

    @GetMapping("/meals")
    public String showMeals(Model model) {
        model.addAttribute("meals", mealRepository.findAll());
        return "meals";
    }

    @GetMapping("/mealweight")
    public String addMealWeight(Model model) {
        IngredientWeight ingredientWeight = new IngredientWeight();
        ingredientWeight.setIngredient(mealIngredientsService.getOneIngredient());
        model.addAttribute("model", ingredientWeight);
        model.addAttribute("name", ingredientWeight.getIngredient().getName());
        return "mealweight";
    }


    @PostMapping("/mealweight")
    public String addMealWeightPost(RedirectAttributes attr, @ModelAttribute("model") IngredientWeight ingredientWeight) {
        ingredientWeightRepository.save(ingredientWeight);
        mealIngredientsService.addIngredientWeight(ingredientWeight);
        if (mealIngredientsService.ingredientsIsNull()) {
            Meal meal = new Meal(mealIngredientsService.getMealForm());
            meal.setIngredientWeights(mealIngredientsService.getSetIngredients());
            meal.setCalories(mealIngredientsService.setMealCalories());
            mealRepository.save(meal);
            mealIngredientsService.clear();
            attr.addFlashAttribute("info", "Dodano posiłek");
            return "redirect:/home";
        }
        return "redirect:/mealweight";
    }

    @GetMapping("/addmeal")
    public String addMeal(Model model) {
        model.addAttribute("meal", new MealForm());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "meal";
    }

    @ResponseBody
    @GetMapping("/removemeal/{mealId}")
    public String removeMeal(@PathVariable Long mealId) {
        mealRepository.deleteById(mealId);
        return "Usunieto danie";
    }

    @PostMapping("/addmeal")
    public String addMealPost(@ModelAttribute("meal") MealForm mealForm) {
        mealForm.setDescription(textToHtml.toHTML(mealForm.getDescription()));
        mealIngredientsService.setIngredients(mealForm.getIngredients());
        mealIngredientsService.setName(mealForm.getName());
        mealIngredientsService.setMealForm(mealForm);
        return "redirect:/mealweight";
    }


}
