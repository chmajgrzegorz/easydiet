package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.entities.User;
import pl.grzegorzchmaj.easydiet.enums.HowManyMeals;
import pl.grzegorzchmaj.easydiet.enums.PhysicalActivity;
import pl.grzegorzchmaj.easydiet.enums.Plans;
import pl.grzegorzchmaj.easydiet.enums.Sex;
import pl.grzegorzchmaj.easydiet.forms.RegisterForm;
import pl.grzegorzchmaj.easydiet.services.UserService;

@Controller
public class MainController {
    private UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String index(Model model) {
        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm());
        }
        model.addAttribute("sex", Sex.values());
        model.addAttribute("physicalActivity", PhysicalActivity.values());
        model.addAttribute("plans", Plans.values());
        model.addAttribute("howManyMeals", HowManyMeals.values());
        return "index";
    }

    @GetMapping("/home")
    public String home(RedirectAttributes attr, Model model) {

        User user = userService.getActualUser();
        model.addAttribute("user", user);
        model.addAttribute("sex", Sex.values());
        model.addAttribute("physicalActivity", PhysicalActivity.values());
        model.addAttribute("plans", Plans.values());
        model.addAttribute("howManyMeals", HowManyMeals.values());
        return "home";
    }
}
