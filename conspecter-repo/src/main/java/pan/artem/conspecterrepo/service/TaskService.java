package pan.artem.conspecterrepo.service;

import dto.outer.SolvedTask;
import dto.outer.TaskDto;

public interface TaskService {

    TaskDto getTask(String username, String repoName, String conspectName);

    TaskDto getCurrentTask(String username);

    SolvedTask checkSolution(String username, String solution);
}
