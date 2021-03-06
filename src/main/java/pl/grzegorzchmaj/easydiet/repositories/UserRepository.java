package pl.grzegorzchmaj.easydiet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grzegorzchmaj.easydiet.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByLogin(String login);
}
