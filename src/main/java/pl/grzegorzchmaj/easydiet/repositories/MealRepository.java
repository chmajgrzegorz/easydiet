package pl.grzegorzchmaj.easydiet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grzegorzchmaj.easydiet.models.entities.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
}
