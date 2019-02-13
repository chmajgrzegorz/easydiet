package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.grzegorzchmaj.easydiet.entities.Ingredient;
import pl.grzegorzchmaj.easydiet.repositories.IngredientRepository;

@Controller
public class IngredientController {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/ingredient")
    public String addIngredient(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "ingredient";
    }

    @PostMapping("/ingredient")
    public String addIngredientPost(@ModelAttribute("ingredient") Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return "redirect:/addmeal";
    }
}
