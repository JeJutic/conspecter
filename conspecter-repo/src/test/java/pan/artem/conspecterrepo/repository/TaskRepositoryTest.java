package pan.artem.conspecterrepo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static pan.artem.conspecterrepo.repository.ConspectRepositoryTest.initialData;

@SpringBootTest
class TaskRepositoryTest {

    @Autowired
    private RepoRepository repoRepository;
    @Autowired
    private ConspectRepository conspectRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        repoRepository.deleteAll();

        initialData(repoRepository, conspectRepository, taskRepository, userRepository);
    }

    @Test
    void findUnsolvedTasks() {
        var tasks = taskRepository.findUnsolvedTasks(
                "user",
                "repo-name",
                "conspect-name"
        );

        assertEquals(1, tasks.size());
        assertEquals("task2-text", tasks.get(0).getText());
        assertEquals("task2 answer", tasks.get(0).getAnswer());
    }

    @Test
    void findAllTasks() {
        var tasks = taskRepository.findAllTasks(
                "repo-name",
                "conspect-name"
        );

        assertEquals(2, tasks.size());
        var text1 = tasks.get(0).getText();
        var text2 = tasks.get(1).getText();
        assertTrue("task1-text".equals(text1) || "task1-text".equals(text2));
        assertTrue("task2-text".equals(text1) || "task2-text".equals(text2));
    }
}