package pl.grzegorzchmaj.easydiet.repositories;

import org.apache.tomcat.jni.Local;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.grzegorzchmaj.easydiet.models.entities.Diet;
import pl.grzegorzchmaj.easydiet.models.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DietRepository extends JpaRepository<Diet, Long> {

    Optional<Diet> findByUserId(Long id);
    Optional<Diet> findByUser(User user);



}
