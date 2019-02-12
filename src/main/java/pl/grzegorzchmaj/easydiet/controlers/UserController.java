package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.enums.HowManyMeals;
import pl.grzegorzchmaj.easydiet.enums.PhysicalActivity;
import pl.grzegorzchmaj.easydiet.enums.Plans;
import pl.grzegorzchmaj.easydiet.enums.Sex;
import pl.grzegorzchmaj.easydiet.entities.User;
import pl.grzegorzchmaj.easydiet.forms.RegisterForm;
import pl.grzegorzchmaj.easydiet.services.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm());
        }
        model.addAttribute("sex", Sex.values());
        model.addAttribute("physicalActivity", PhysicalActivity.values());
        model.addAttribute("plans", Plans.values());
        model.addAttribute("howManyMeals", HowManyMeals.values());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerForm") @Valid RegisterForm registerForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("info", "Wypełnij poprawnie formularz");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerForm", bindingResult);
            redirectAttributes.addFlashAttribute("registerForm", registerForm);
            return "redirect:/register";
        }
        User user = new User(registerForm);

        userService.save(user);

        return "redirect:/";
    }

    @PostMapping("/update")
    public String afterUpdate(@ModelAttribute("registerForm") @Valid RegisterForm registerForm, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("info", "Wypełnij poprawnie formularz");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerForm", bindingResult);
            redirectAttributes.addFlashAttribute("registerForm", registerForm);
            return "redirect:/home";
        }
        User user = userService.getActualUser();
        user.update(registerForm);
        userService.update(user);
        redirectAttributes.addFlashAttribute("info", "Dane zaktualizowane");
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String accessDenied(Model model) {
            User user = userService.getActualUser();
            String message = "Czesc " + user.getLogin() + "<br> Nie posiadasz uprawnien do tej strony!";
            model.addAttribute("message", message);
        return "403page";
    }

    @GetMapping("/logoutSuccessful")
    public String logout(RedirectAttributes attr){
        attr.addFlashAttribute("info", "Wylogowano");
        return "redirect:/home";
    }

}
