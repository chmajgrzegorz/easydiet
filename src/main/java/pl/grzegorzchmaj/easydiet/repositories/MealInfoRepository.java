package pl.grzegorzchmaj.easydiet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.grzegorzchmaj.easydiet.entities.MealInfo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealInfoRepository extends JpaRepository<MealInfo, Long> {


    @Query(value = "SELECT DISTINCT Date FROM Meal_Info WHERE Date BETWEEN :start AND :end", nativeQuery = true)
    Optional<List<LocalDate>> findDatesBetweenStartAndEndDate(@Param("start") Date start, @Param("end") Date end);

}
