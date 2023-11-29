package pan.artem.conspecterrepo.service;

import dto.ConspectDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pan.artem.conspecterrepo.repository.ConspectRepository;
import pan.artem.conspecterrepo.repository.RepoRepository;
import pan.artem.conspecterrepo.repository.TaskRepository;
import pan.artem.conspecterrepo.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static pan.artem.conspecterrepo.repository.ConspectRepositoryTest.initialData;

@SpringBootTest
class ConspectServiceImplTest {

    @Autowired
    private RepoRepository repoRepository;
    @Autowired
    private ConspectRepository conspectRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConspectServiceImpl conspectService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        repoRepository.deleteAll();

        initialData(repoRepository, conspectRepository, taskRepository, userRepository);
    }

    @Test
    void findAllByRepoName() {
        var conspects = conspectService.findAllByRepoName(
                "user", "repo-name"
        );

        assertEquals(1, conspects.size());
        assertEquals(new ConspectDto(
            "conspect-name",
                2,
                1
        ), conspects.get(0));
    }
}