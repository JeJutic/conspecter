package pan.artem.conspecterrepo.domain;

import dto.outer.SolvedTask;

public interface SolutionVerifier {
    SolvedTask verify(String answer, String solution);
}
