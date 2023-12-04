package pan.artem.conspecterrepo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pan.artem.conspecterrepo.entity.Repository;

import java.util.List;

public interface RepoRepository extends JpaRepository<Repository, Long> {

//    @Query("SELECT r FROM Repository r LEFT JOIN FETCH r.conspects WHERE r.author = :author AND r.name = :name")
    List<Repository> findAllByAuthorAndName(@Param("author") String author, @Param("name") String name);
}
