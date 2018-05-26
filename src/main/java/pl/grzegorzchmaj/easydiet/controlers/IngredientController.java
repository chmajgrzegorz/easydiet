package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.models.entities.Ingredient;
import pl.grzegorzchmaj.easydiet.models.services.UserInfoService;
import pl.grzegorzchmaj.easydiet.repositories.IngredientRepository;

@Controller
public class IngredientController {

    UserInfoService userInfoService;
    IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(UserInfoService userInfoService, IngredientRepository ingredientRepository) {
        this.userInfoService = userInfoService;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/ingredient")
    public String addIngredient(Model model, RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        model.addAttribute("ingredient", new Ingredient());
        return "ingredient";
    }

    @PostMapping("/ingredient")
    public String addIngredientPost(@ModelAttribute("ingredient") Ingredient ingredient, RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        ingredientRepository.save(ingredient);
        System.out.println(ingredient.getName());
        return "redirect:/addmeal";
    }
}
