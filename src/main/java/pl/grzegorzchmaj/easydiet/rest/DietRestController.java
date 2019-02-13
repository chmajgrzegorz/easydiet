package pl.grzegorzchmaj.easydiet.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorzchmaj.easydiet.entities.Diet;
import pl.grzegorzchmaj.easydiet.entities.MealInfo;
import pl.grzegorzchmaj.easydiet.entities.User;
import pl.grzegorzchmaj.easydiet.exceptions.DietException;
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

@RestController
@RequestMapping("/api")
public class DietRestController {

    private DietRepository dietRepository;
    private DietMealsService dietMealsService;
    private MealInfoRepository mealInfoRepository;
    private UserService userService;

    @Autowired
    public DietRestController(DietRepository dietRepository, UserService userService,
                          DietMealsService dietMealsService, MealInfoRepository mealInfoRepository) {
        this.dietRepository = dietRepository;
        this.dietMealsService = dietMealsService;
        this.mealInfoRepository = mealInfoRepository;
        this.userService = userService;
    }


    @PostMapping("/diets/{startDateString}/{endDateString}")
    public ResponseEntity createDiet(@PathVariable("startDateString") String startDateString,
                                     @PathVariable("endDateString") String endDateString){
        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);
        if(endDate.isBefore(startDate)){
            throw new DietException("End date is before start date");
        }
        DietForm dietForm = new DietForm();
        User user = userService.getActualUser();
        dietForm.setUser(user);
        dietForm.setStartDate(Date.valueOf(startDate));
        dietForm.setEndDate(Date.valueOf(endDate));
        dietMealsService.removePreviousDietIfPresent(user);
        dietMealsService.setMealsToDiet(new Diet(dietForm), user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/diets")
    public Diet getDiet(){
        Optional<Diet> diet = dietRepository.findByUser(userService.getActualUser());
        if(!diet.isPresent()){
            throw new DietException("You don't have diet");
        }
        return diet.get();
    }

}
