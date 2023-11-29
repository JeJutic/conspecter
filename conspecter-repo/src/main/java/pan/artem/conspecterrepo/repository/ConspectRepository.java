package pan.artem.conspecterrepo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pan.artem.conspecterrepo.entity.Conspect;

import java.util.List;

public interface ConspectRepository extends JpaRepository<Conspect, Long> {

    @Query("SELECT c FROM Conspect c WHERE c.repository.name = :repoName")
    List<Conspect> findAllByRepoName(@Param("repoName") String repoName);

    @Query("SELECT count(*) FROM Conspect c LEFT JOIN c.tasks WHERE c = :conspect")
    int countTasks(@Param("conspect") Conspect conspect);

    @Query("SELECT count(*) FROM User u LEFT JOIN u.tasksCompleted t " +
            "WHERE t.conspect = :conspect AND u.name = :username")
    int countSolvedTasks(@Param("username") String username,
                         @Param("conspect") Conspect conspect);
}
