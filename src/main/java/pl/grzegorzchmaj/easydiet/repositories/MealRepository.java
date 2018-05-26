package pl.grzegorzchmaj.easydiet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grzegorzchmaj.easydiet.models.entities.Meal;

import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findByName(String name);

    @Query(value = "SELECT * FROM meal  WHERE category_id = 1 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Meal findRandomBreakfast();

    @Query(value = "SELECT * FROM meal  WHERE category_id = 2 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Meal findRandomMeal();

    @Query(value = "SELECT * FROM meal  WHERE category_id = 6 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Meal findRandomDinner();

}
