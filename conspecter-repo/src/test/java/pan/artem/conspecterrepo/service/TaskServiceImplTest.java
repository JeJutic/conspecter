package pan.artem.conspecterrepo.service;

import dto.outer.SolvedTask;
import dto.outer.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pan.artem.conspecterrepo.TestDataSampleService;
import pan.artem.conspecterrepo.exception.ResourceNotFoundException;
import pan.artem.conspecterrepo.repository.TaskRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceImplTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskServiceImpl taskService;
    @Autowired
    private TestDataSampleService testDataSampleService;

    @BeforeEach
    void setUp() {
        testDataSampleService.setUp();
    }

    @Test
    void getTask() {
        var task = taskService.getTask(
                "user", "repo-name", "conspect-name"
        );

        assertEquals(
                new TaskDto("repo-name", "conspect-name", "task2-text"),
                task
        );
    }

    @Test
    void getTaskNull() {
        var task2 = taskRepository.findUnsolvedTasks(
                "user", "repo-name", "conspect-name"
        ).get(0);
        taskRepository.addUserSolved("user", task2.getId());

        var task = taskService.getTask(
                "user", "repo-name", "conspect-name"
        );

        assertNull(task);
    }

    @Test
    void getCurrentTask() {
        taskService.getTask(
                "user", "repo-name", "conspect-name"
        );

        var task = taskService.getCurrentTask("user");

        assertEquals(
                new TaskDto("repo-name", "conspect-name", "task2-text"),
                task
        );
    }

    @Test
    void getCurrentTaskNull() {
        var task = taskService.getCurrentTask("user");

        assertNull(task);
    }

    @Test
    void checkSolution() {
        taskService.getTask(
                "user", "repo-name", "conspect-name"
        );

        var solvedTask = taskService.checkSolution("user", "task2 answer");

        assertEquals(
                new SolvedTask("task2 answer", 2, 2, true),
                solvedTask
        );
        assertNull(taskService.getCurrentTask("user"));
        assertNull(taskService.getTask(
                "user", "repo-name", "conspect-name"
        ));
    }

    @Test
    void checkSolutionHalf() {
        taskService.getTask(
                "user", "repo-name", "conspect-name"
        );

        var solvedTask = taskService.checkSolution("user", "half answer");

        assertFalse(solvedTask.status());
        assertNull(taskService.getCurrentTask("user"));
        assertNotNull(taskService.getTask(
                "user", "repo-name", "conspect-name"
        ));
    }

    @Test
    void checkSolutionException() {
        assertThrows(ResourceNotFoundException.class, () ->
            taskService.checkSolution("user", "task2 answer")
        );
    }
}