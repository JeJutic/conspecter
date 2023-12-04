package pan.artem.conspecterrepo.domain;

import dto.outer.SolvedTask;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SolutionVerifierImpl implements SolutionVerifier {

    @Override
    public SolvedTask verify(String answer, String solution) {
        var rightWords = Arrays.stream(answer.split("\\s"))
                .filter(s -> !s.isEmpty())
                .toList();
        var solved = Arrays.stream(solution.split("\\s"))
                .filter(s -> !s.isEmpty())
                .toList();

        int score = 0;
        for (int i = 0; i < Math.min(rightWords.size(), solved.size()); i++) {
            if (rightWords.get(i).equals(solved.get(i))) {
                score++;
            }
        }
        boolean status = (double) score / rightWords.size() > 0.7;

        return new SolvedTask(answer, score, rightWords.size(), status);
    }
}
