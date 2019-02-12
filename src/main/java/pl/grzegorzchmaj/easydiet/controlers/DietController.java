package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.entities.Diet;
import pl.grzegorzchmaj.easydiet.entities.MealInfo;
import pl.grzegorzchmaj.easydiet.entities.User;
import pl.grzegorzchmaj.easydiet.forms.DietForm;
import pl.grzegorzchmaj.easydiet.repositories.DietRepository;
import pl.grzegorzchmaj.easydiet.repositories.MealInfoRepository;
import pl.grzegorzchmaj.easydiet.services.DietMealsService;
import pl.grzegorzchmaj.easydiet.services.UserService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class DietController {

    private DietRepository dietRepository;
    private DietMealsService dietMealsService;
    private MealInfoRepository mealInfoRepository;
    private UserService userService;

    @Autowired
    public DietController(DietRepository dietRepository, UserService userService,
                          DietMealsService dietMealsService, MealInfoRepository mealInfoRepository) {
        this.dietRepository = dietRepository;
        this.dietMealsService = dietMealsService;
        this.mealInfoRepository = mealInfoRepository;
        this.userService = userService;
    }

    @GetMapping("/creatediet")
    public String createDiet(Model model) {
        User user = userService.getActualUser();
        DietForm dietForm = new DietForm();
        dietForm.setUser(user);
        model.addAttribute("dietForm", dietForm);
        return "creatediet";
    }

    @PostMapping("/creatediet")
    public String createDietPost(@ModelAttribute("diet") DietForm dietForm, RedirectAttributes attr) {
        User user = userService.getActualUser();
        Diet diet = new Diet(dietForm);
        dietMealsService.removePreviousDietIfPresent(user);
        dietMealsService.setMealsToDiet(diet, user);
        attr.addFlashAttribute("info", "Dodano pomyślnie dietę");
        return "redirect:/diet/" + diet.getStartDate();
    }

    @GetMapping("/diet/{date}")
    public String diet(@PathVariable("date") String dateString, Model model, RedirectAttributes attr) {
        Optional<Diet> diet = dietRepository.findByUser(userService.getActualUser());
        if (!diet.isPresent()) {
            attr.addFlashAttribute("info", "Pierwsze utwórz dietę");
            return "redirect:/creatediet";
        }
        Date startDate = Date.valueOf(diet.get().getStartDate());
        Date endDate = Date.valueOf(diet.get().getEndDate());
        LocalDate date = LocalDate.parse(dateString);
        List<LocalDate> dates = mealInfoRepository.findDatesBetweenStartAndEndDate(startDate, endDate).get();
        List<MealInfo> meals = diet.get().getMeals().stream().filter(s -> s.getDate().equals(date)).sorted().collect(Collectors.toList());
        dietMealsService.adjustMealsIngredients(meals, userService.getActualUser());
        model.addAttribute("diet", diet.get());
        model.addAttribute("mealsInfo", meals);
        model.addAttribute("dates", dates);
        return "diet";
    }
}

