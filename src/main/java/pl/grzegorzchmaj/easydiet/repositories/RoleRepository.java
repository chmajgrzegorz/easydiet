package pl.grzegorzchmaj.easydiet.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grzegorzchmaj.easydiet.entities.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query(value = "SELECT * FROM role WHERE role.role_name = 'USER'", nativeQuery = true)
    List<Role> findDefaultRoles();
}
