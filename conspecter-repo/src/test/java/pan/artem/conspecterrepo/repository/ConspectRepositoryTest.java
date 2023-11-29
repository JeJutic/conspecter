package pan.artem.conspecterrepo.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pan.artem.conspecterrepo.entity.Conspect;
import pan.artem.conspecterrepo.entity.Repository;
import pan.artem.conspecterrepo.entity.Task;
import pan.artem.conspecterrepo.entity.User;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConspectRepositoryTest {

    @Autowired
    private RepoRepository repoRepository;
    @Autowired
    private ConspectRepository conspectRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    public static void initialData(RepoRepository repoRepository,
                                   ConspectRepository conspectRepository,
                                   TaskRepository taskRepository,
                                   UserRepository userRepository
    ) {
        var repo = new Repository();
        repo.setName("repo-name");
        repo.setAuthor("repo-author");
        repo = repoRepository.save(repo);

        var conspect = new Conspect();
        conspect.setName("conspect-name");
        conspect.setRepository(repo);
        conspect = conspectRepository.save(conspect);

        var task1 = new Task();
        task1.setText("task1-text");
        task1.setAnswer("task1 answer");
        task1.setConspect(conspect);
        taskRepository.save(task1);

        var task2 = new Task();
        task2.setText("task2-text");
        task2.setAnswer("task2 answer");
        task2.setConspect(conspect);
        taskRepository.save(task2);

        var user = new User();
        user.setName("user");
        Set<Task> tasksCompleted = new HashSet<>();
        tasksCompleted.add(task1);
        user.setTasksCompleted(tasksCompleted);
        userRepository.save(user);
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        repoRepository.deleteAll();

        initialData(repoRepository, conspectRepository, taskRepository, userRepository);
    }

    @Test
    void findAllByRepoName() {
        var conspects = conspectRepository.findAllByRepoName("repo-name");

        assertEquals(1, conspects.size());
        assertEquals("conspect-name", conspects.get(0).getName());
    }

    @Test
    void countTasks() {
        var conspect = conspectRepository.findAllByRepoName("repo-name").get(0);
        int count = conspectRepository.countTasks(conspect);

        assertEquals(2, count);
    }

    @Test
    void countSolvedTasks() {
        var conspect = conspectRepository.findAllByRepoName("repo-name").get(0);
        int count = conspectRepository.countSolvedTasks("user", conspect);

        assertEquals(1, count);
    }
}