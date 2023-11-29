package pan.artem.conspecterrepo.domain;

import dto.SolvedTask;
import org.springframework.stereotype.Component;

@Component
public class SolutionVerifierImpl implements SolutionVerifier {

    @Override
    public SolvedTask verify(String answer, String solution) {
        String[] rightWords = answer.split("\\s");
        var solved = solution.split("\\s");

        int score = 0;
        for (int i = 0; i < Math.min(rightWords.length, solved.length); i++) {
            if (rightWords[i].equals(solved[i])) {
                score++;
            }
        }
        boolean status = (double) score / rightWords.length > 0.7;

        return new SolvedTask(answer, score, rightWords.length, status);
    }
}
