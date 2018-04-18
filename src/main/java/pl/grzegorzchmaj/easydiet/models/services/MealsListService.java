package pl.grzegorzchmaj.easydiet.models.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import pl.grzegorzchmaj.easydiet.models.entities.Meal;

import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor
public class MealsListService {

    private List<Meal> meals;
}
