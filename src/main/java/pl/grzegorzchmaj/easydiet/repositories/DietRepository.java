package pl.grzegorzchmaj.easydiet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.grzegorzchmaj.easydiet.entities.Diet;
import pl.grzegorzchmaj.easydiet.entities.User;

import java.util.Optional;

public interface DietRepository extends JpaRepository<Diet, Long> {

    Optional<Diet> findByUserId(Long id);
    Optional<Diet> findByUser(User user);



}
