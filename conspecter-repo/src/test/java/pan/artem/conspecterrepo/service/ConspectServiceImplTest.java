package pan.artem.conspecterrepo.service;

import dto.outer.ConspectDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pan.artem.conspecterrepo.TestDataSampleService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConspectServiceImplTest {

    @Autowired
    private ConspectServiceImpl conspectService;
    @Autowired
    private TestDataSampleService testDataSampleService;

    @BeforeEach
    void setUp() {
        testDataSampleService.setUp();
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