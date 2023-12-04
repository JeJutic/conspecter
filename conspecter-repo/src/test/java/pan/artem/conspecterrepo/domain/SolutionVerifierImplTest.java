package pan.artem.conspecterrepo.domain;

import dto.outer.SolvedTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionVerifierImplTest {

    private SolutionVerifierImpl solutionVerifier;

    @BeforeEach
    void init() {
        solutionVerifier = new SolutionVerifierImpl();
    }

    @Test
    void verify() {
        assertAll(
                () -> assertEquals(
                        new SolvedTask("right answer", 2, 2, true),
                        solutionVerifier.verify("right answer", "right answer")
                ),
                () -> assertEquals(
                        new SolvedTask("right answer", 2, 2, true),
                        solutionVerifier.verify("right answer", " right    answer ")
                ),
                () -> assertEquals(
                        new SolvedTask("right answer", 1, 2, false),
                        solutionVerifier.verify("right answer", "left answer")
                ),
                () -> assertEquals(
                        new SolvedTask("right answer", 1, 2, false),
                        solutionVerifier.verify("right answer", "     left answer")
                ),
                () -> assertEquals(
                        new SolvedTask("right answer", 1, 2, false),
                        solutionVerifier.verify("right answer", "right")
                ),
                () -> assertEquals(
                        new SolvedTask("right answer", 0, 2, false),
                        solutionVerifier.verify("right answer", "false false")
                )
        );
    }
}