package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.enums.PhysicalActivity;
import pl.grzegorzchmaj.easydiet.enums.Plans;
import pl.grzegorzchmaj.easydiet.enums.Sex;
import pl.grzegorzchmaj.easydiet.models.entities.User;
import pl.grzegorzchmaj.easydiet.models.forms.RegisterForm;
import pl.grzegorzchmaj.easydiet.models.services.UserInfoService;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

@Controller
public class MainController {

    UserInfoService userInfoService;
    UserRepository userRepository;

    @Autowired
    public MainController(UserInfoService userInfoService, UserRepository userRepository) {
        this.userInfoService = userInfoService;
        this.userRepository = userRepository;
    }





    @GetMapping("/home")
    public String home(RedirectAttributes attr, Model model){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        User user = userRepository.findById(userInfoService.getUser().getId()).get();
        model.addAttribute("user", user);
        model.addAttribute("sex", Sex.values());
        model.addAttribute("physicalActivity", PhysicalActivity.values());
        model.addAttribute("plans", Plans.values());
        return "home";
    }
}
