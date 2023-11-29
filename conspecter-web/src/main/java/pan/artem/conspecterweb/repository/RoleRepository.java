package pan.artem.conspecterweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pan.artem.conspecterweb.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
