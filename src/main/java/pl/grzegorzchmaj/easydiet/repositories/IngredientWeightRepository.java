package pl.grzegorzchmaj.easydiet.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grzegorzchmaj.easydiet.entities.IngredientWeight;

@Repository
public interface IngredientWeightRepository extends JpaRepository<IngredientWeight, Long> {
}
