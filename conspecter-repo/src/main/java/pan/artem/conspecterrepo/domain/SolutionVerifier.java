package pan.artem.conspecterrepo.domain;

import dto.SolvedTask;

public interface SolutionVerifier {
    SolvedTask verify(String answer, String solution);
}
