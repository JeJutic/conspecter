package pan.artem.conspecterrepo.service;

import dto.SolvedTask;
import dto.TaskDto;

public interface TaskService {

    TaskDto getTask(String username, String repoName, String conspectName);

    TaskDto getCurrentTask(String username);

    SolvedTask checkSolution(String username, String solution);
}
