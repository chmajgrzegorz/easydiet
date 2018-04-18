package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.enums.PhysicalActivity;
import pl.grzegorzchmaj.easydiet.enums.Plans;
import pl.grzegorzchmaj.easydiet.enums.Sex;
import pl.grzegorzchmaj.easydiet.models.entities.User;
import pl.grzegorzchmaj.easydiet.models.forms.LoginForm;
import pl.grzegorzchmaj.easydiet.models.forms.RegisterForm;
import pl.grzegorzchmaj.easydiet.models.services.UserInfoService;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    UserRepository userRepository;
    UserInfoService userInfoService;

    @Autowired
    public UserController(UserRepository userRepository, UserInfoService userInfoService) {
        this.userRepository = userRepository;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/register")
    public String register(Model model){
        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm());
        }
        model.addAttribute("sex", Sex.values());
        model.addAttribute("physicalActivity", PhysicalActivity.values());
        model.addAttribute("plans", Plans.values());
        return "register";
    }

    @PostMapping("/register")
    public String afterRegister(@ModelAttribute("registerForm") @Valid RegisterForm registerForm, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Model model){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("info", "Wypełnij poprawnie formularz");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerForm", bindingResult);
            redirectAttributes.addFlashAttribute("registerForm", registerForm);
            return "redirect:/register";
        }
        User user = new User(registerForm);
        userRepository.save(user);
        userInfoService.setUser(user);
        userInfoService.setLogged(true);
        redirectAttributes.addFlashAttribute("info", "Zarejestrowałeś się poprawnie");
        return "redirect:/register";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String afterLogin(@RequestParam("login") String login, @RequestParam("password") String password, RedirectAttributes attr, Model model){
        Optional<User> logged =  userRepository.findByLoginAndPassword(login, password);
        if(logged.isPresent()) {
            userInfoService.setUser(logged.get());
            userInfoService.setLogged(true);
            attr.addFlashAttribute("info", "Zalogowano użytkownika");
            return "redirect:/login";
        }
        else {
            attr.addFlashAttribute("info", "Nie ma takiego użytkownika");
            return "redirect:/login";
        }

    }
}
