package pl.grzegorzchmaj.easydiet.models.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import pl.grzegorzchmaj.easydiet.models.entities.Diet;
import pl.grzegorzchmaj.easydiet.models.entities.MealInfo;

import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Data
@NoArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DietMealsService {

    private List<MealInfo> meals = new ArrayList<>();

    public void addMealsToList(Diet diet){
        Long days = DAYS.between(diet.getStartDate(), diet.getEndDate())+1;
        for (int i = 0; i < days; i++) {
            for(int j = 0 ; j<diet.getUser().getMeals().getHowMany() ; j++){
                meals.add(new MealInfo(diet.getStartDate().plusDays(i),"Posiłek"  + j+1));
            }
        }
    }
}
