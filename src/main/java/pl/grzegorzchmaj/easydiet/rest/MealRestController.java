package pl.grzegorzchmaj.easydiet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorzchmaj.easydiet.entities.Meal;
import pl.grzegorzchmaj.easydiet.exceptions.MealException;
import pl.grzegorzchmaj.easydiet.repositories.CategoryRepository;
import pl.grzegorzchmaj.easydiet.repositories.IngredientRepository;
import pl.grzegorzchmaj.easydiet.repositories.IngredientWeightRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealRepository;
import pl.grzegorzchmaj.easydiet.services.MealIngredientsService;
import pl.grzegorzchmaj.easydiet.services.TextToHtml;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MealRestController {

    private MealRepository mealRepository;
    private IngredientRepository ingredientRepository;
    private CategoryRepository categoryRepository;
    private IngredientWeightRepository ingredientWeightRepository;
    private MealIngredientsService mealIngredientsService;
    private TextToHtml textToHtml;

    @Autowired
    public MealRestController(MealRepository mealRepository,
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

    @PostMapping("/meals")
    public ResponseEntity addMeal(@RequestBody Meal meal){
        mealRepository.save(meal);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/meals/{mealId}")
    public Meal showMeals(@PathVariable Long mealId) {
        Optional<Meal> mealOptional = mealRepository.findById(mealId);
        if(mealOptional.isPresent()){
            return mealOptional.get();
        }
        throw new MealException("Meal id not found - " + mealId);
    }

}
