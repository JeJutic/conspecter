package pan.artem.conspecterrepo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskMakerImplTest {

    private TaskMakerImpl taskMaker;

    @BeforeEach
    void init() {
        taskMaker = new TaskMakerImpl();
    }

    @Test
    void makeTasks() {
        String text = "The text which makes sense. And some formulas: $x + y = 7 \\int_a b$";

        var tasks = taskMaker.makeTasks(text, 3);

        assertEquals(3, tasks.size());
        tasks.forEach(t -> assertFalse(t.text().isEmpty()));
        tasks.forEach(t -> assertFalse(t.answer().isEmpty()));
    }

    @Test
    void makeTasksUnsuccessful() {
        String text = "i 2342 i342 a34ab 2Ai why_not why_not";

        var tasks = taskMaker.makeTasks(text, 3);

        assertTrue(tasks.isEmpty());
    }
}