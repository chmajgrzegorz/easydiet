package pl.grzegorzchmaj.easydiet.repositories;

import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.grzegorzchmaj.easydiet.models.entities.MealInfo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealInfoRepository extends JpaRepository<MealInfo, Long> {


    @Query(value = "SELECT DISTINCT Date FROM MealInfo WHERE Date BETWEEN :start AND :end", nativeQuery = false)
    Optional<List<LocalDate>> findDatesBetweenStartAndEndDate(@Param("start") Date start, @Param("end") Date end);

}
