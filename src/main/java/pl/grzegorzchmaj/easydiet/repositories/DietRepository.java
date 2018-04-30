package pl.grzegorzchmaj.easydiet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.grzegorzchmaj.easydiet.models.entities.Diet;

public interface DietRepository extends JpaRepository<Diet, Long> {
}
