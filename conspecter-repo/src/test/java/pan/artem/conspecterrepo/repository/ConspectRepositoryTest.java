package pan.artem.conspecterrepo.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pan.artem.conspecterrepo.TestDataSampleService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConspectRepositoryTest {

    @Autowired
    private ConspectRepository conspectRepository;
    @Autowired
    private TestDataSampleService testDataSampleService;

    @BeforeEach
    void setUp() {
        testDataSampleService.setUp();
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