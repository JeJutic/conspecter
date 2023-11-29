package pan.artem.conspecterrepo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pan.artem.conspecterrepo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
