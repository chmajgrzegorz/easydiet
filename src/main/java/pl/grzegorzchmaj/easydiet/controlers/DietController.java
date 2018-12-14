package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.models.entities.Diet;
import pl.grzegorzchmaj.easydiet.models.entities.MealInfo;
import pl.grzegorzchmaj.easydiet.models.entities.User;
import pl.grzegorzchmaj.easydiet.models.forms.DietForm;
import pl.grzegorzchmaj.easydiet.models.services.DietMealsService;
import pl.grzegorzchmaj.easydiet.models.services.UserInfoService;
import pl.grzegorzchmaj.easydiet.repositories.DietRepository;
import pl.grzegorzchmaj.easydiet.repositories.IngredientRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealInfoRepository;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class DietController {

    UserInfoService userInfoService;
    DietRepository dietRepository;
    UserRepository userRepository;
    DietMealsService dietMealsService;
    IngredientRepository ingredientRepository;
    MealInfoRepository mealInfoRepository;

    @Autowired
    public DietController(UserInfoService userInfoService, DietRepository dietRepository,
                          UserRepository userRepository, IngredientRepository ingredientRepository,
                          DietMealsService dietMealsService, MealInfoRepository mealInfoRepository) {
        this.userInfoService = userInfoService;
        this.dietRepository = dietRepository;
        this.userRepository = userRepository;
        this.dietMealsService = dietMealsService;
        this.ingredientRepository = ingredientRepository;
        this.mealInfoRepository = mealInfoRepository;
    }

    @GetMapping("/creatediet")
    public String createDiet(Model model, RedirectAttributes attr) {
        if (!userInfoService.isLogged()) {
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
    public String createDietPost(@ModelAttribute("diet") DietForm dietForm, RedirectAttributes attr) {
        if (!userInfoService.isLogged()) {
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        Diet diet = new Diet(dietForm);
        dietMealsService.removePreviousDietIfPresent();
        dietMealsService.setMealsToDiet(diet);
        attr.addFlashAttribute("info", "Dodano pomyślnie dietę");
        return "redirect:/diet/" + diet.getStartDate();
    }

    @GetMapping("/diet/{date}")
    public String diet(@PathVariable("date") String dateString, Model model, RedirectAttributes attr) {
        if (!userInfoService.isLogged()) {
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        Optional<Diet> diet = dietRepository.findByUser(userInfoService.getUser());
        if (!diet.isPresent()) {
            attr.addFlashAttribute("info", "Pierwsze utwórz dietę");
            return "redirect:/creatediet";
        }
        Date startDate = Date.valueOf(diet.get().getStartDate());
        Date endDate = Date.valueOf(diet.get().getEndDate());
        LocalDate date = LocalDate.parse(dateString);
        List<LocalDate> dates = mealInfoRepository.findDatesBetweenStartAndEndDate(startDate, endDate).get();
        List<MealInfo> meals = diet.get().getMeals().stream().filter(s -> s.getDate().equals(date)).sorted().collect(Collectors.toList());
        dietMealsService.adjustIngredients(meals);
        model.addAttribute("diet", diet.get());
        model.addAttribute("mealsInfo", meals);
        model.addAttribute("dates", dates);
        return "diet";
    }
}

