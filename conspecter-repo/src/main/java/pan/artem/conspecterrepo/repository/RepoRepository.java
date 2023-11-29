package pan.artem.conspecterrepo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pan.artem.conspecterrepo.entity.Repository;

public interface RepoRepository extends JpaRepository<Repository, Long> {
}
