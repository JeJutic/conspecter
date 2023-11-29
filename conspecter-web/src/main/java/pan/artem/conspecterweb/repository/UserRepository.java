package pan.artem.conspecterweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pan.artem.conspecterweb.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
