package pan.artem.conspecterrepo.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pan.artem.conspecterrepo.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.conspect.repository.name = :repoName " +
            "AND t.conspect.name = :conspectName AND t NOT IN " +
            "(SELECT u.tasksCompleted FROM User u WHERE u.name = :username)")
    List<Task> findUnsolvedTasks(@Param("username") String username,
                                 @Param("repoName") String repoName,
                                 @Param("conspectName") String conspectName);

    @Query("SELECT t FROM Task t WHERE t.conspect.repository.name = :repoName " +
            "AND t.conspect.name = :conspectName")
    List<Task> findAllTasks(@Param("repoName") String repoName,
                            @Param("conspectName") String conspectName);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO user_task(user_id, task_id) VALUES (?1, ?2)")
    void addUserSolved(String user, long taskId);
}
