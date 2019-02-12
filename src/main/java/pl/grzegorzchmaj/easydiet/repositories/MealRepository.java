package pl.grzegorzchmaj.easydiet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grzegorzchmaj.easydiet.entities.Meal;

import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findByName(String name);

    @Query(value = "SELECT * FROM meal JOIN meal_category ON meal.id = meal_category.meal_id WHERE meal_category.category_id = 1 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Meal findRandomBreakfast();

    @Query(value = "SELECT * FROM meal JOIN meal_category ON meal.id = meal_category.meal_id WHERE meal_category.category_id = 2 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Meal findRandomLunch();

    @Query(value = "SELECT * FROM meal JOIN meal_category ON meal.id = meal_category.meal_id WHERE meal_category.category_id = 6 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Meal findRandomDinner();

    @Query(value = "SELECT * FROM meal JOIN meal_category ON meal.id = meal_category.meal_id WHERE meal_category.category_id = 7 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Meal findRandomTea();

    @Query(value = "SELECT * FROM meal JOIN meal_category ON meal.id = meal_category.meal_id WHERE meal_category.category_id = 8 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Meal findRandomSupper();

    @Query(value = "SELECT * FROM meal JOIN meal_category ON meal.id = meal_category.meal_id WHERE meal_category.category_id = 9 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Meal findRandomDessert();

}
