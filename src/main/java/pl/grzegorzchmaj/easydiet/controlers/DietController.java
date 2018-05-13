package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.models.entities.Diet;
import pl.grzegorzchmaj.easydiet.models.entities.MealInfo;
import pl.grzegorzchmaj.easydiet.models.entities.User;
import pl.grzegorzchmaj.easydiet.models.forms.DietForm;
import pl.grzegorzchmaj.easydiet.models.services.DietMealsService;
import pl.grzegorzchmaj.easydiet.models.services.UserInfoService;
import pl.grzegorzchmaj.easydiet.repositories.DietRepository;
import pl.grzegorzchmaj.easydiet.repositories.IngredientRepository;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


@Controller
public class DietController {

    UserInfoService userInfoService;
    DietRepository dietRepository;
    UserRepository userRepository;
    DietMealsService dietMealsService;
    IngredientRepository ingredientRepository;

    @Autowired
    public DietController(UserInfoService userInfoService, DietRepository dietRepository,
                          UserRepository userRepository, IngredientRepository ingredientRepository,
                          DietMealsService dietMealsService) {
        this.userInfoService = userInfoService;
        this.dietRepository = dietRepository;
        this.userRepository = userRepository;
        this.dietMealsService = dietMealsService;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/creatediet")
    public String createDiet(Model model, RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        User user = userInfoService.getUser();
        DietForm dietForm = new DietForm();
        dietForm.setUser(user);
            model.addAttribute("dietForm", dietForm);
            return "creatediet";
    }

    @PostMapping("/creatediet")
    public String createDietPost(@ModelAttribute("diet") DietForm dietForm, RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        Diet diet = new Diet(dietForm);
        Optional<Diet> previousDiet = dietRepository.findByUserId(userInfoService.getUser().getId());
        if(previousDiet.isPresent()){
            dietRepository.deleteById(previousDiet.get().getId());
        }
        dietMealsService.setMealsToDiet(diet);
        attr.addFlashAttribute("info", "Dodano pomyślnie dietę");
        return "redirect:/diet";
    }
}

