package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.models.entities.Diet;
import pl.grzegorzchmaj.easydiet.models.entities.User;
import pl.grzegorzchmaj.easydiet.models.forms.DietForm;
import pl.grzegorzchmaj.easydiet.models.services.DietMealsService;
import pl.grzegorzchmaj.easydiet.models.services.UserInfoService;
import pl.grzegorzchmaj.easydiet.repositories.DietRepository;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.util.Optional;


@Controller
public class DietController {

    UserInfoService userInfoService;
    DietRepository dietRepository;
    UserRepository userRepository;
    DietMealsService dietMealsService;

    @Autowired
    public DietController(UserInfoService userInfoService, DietRepository dietRepository,
                          UserRepository userRepository, DietMealsService dietMealsService) {
        this.userInfoService = userInfoService;
        this.dietRepository = dietRepository;
        this.userRepository = userRepository;
        this.dietMealsService = dietMealsService;
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
        Diet diet = new Diet(dietForm);
        Optional<Diet> previousDiet = dietRepository.findByUser(userInfoService.getUser());
        if(previousDiet.isPresent()){
            dietRepository.delete(previousDiet.get());
        }
        dietRepository.save(diet);
        User user = userRepository.findByLoginAndPassword(userInfoService.getUser().getLogin(), userInfoService.getUser().getPassword()).get();
        user.setDiet(diet);
        userRepository.save(user);
        dietMealsService.addMealsToList(diet);
        attr.addFlashAttribute("info", "Dodano pomyślnie dietę");
        return "redirect:/addmealstodiet";
    }

    @GetMapping("/addmealstodiet")
    public String addmealsToDiet(Model model, RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        if(dietMealsService.getMeals().size()==0){
            dietMealsService.addMealsToList(userInfoService.getUser().getDiet());
        }
        User user = userInfoService.getUser();
        DietForm dietForm = new DietForm();
        dietForm.setUser(user);
        model.addAttribute("info2", "Dodaj posiłki:");
        model.addAttribute("dietForm", dietForm);
        model.addAttribute("user", user);
        model.addAttribute("meals", dietMealsService.getMeals());
        return "creatediet";
    }
}

