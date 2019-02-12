package pl.grzegorzchmaj.easydiet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grzegorzchmaj.easydiet.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
