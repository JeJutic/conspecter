package pan.artem.conspecterrepo.service;

import dto.outer.SolvedTask;
import dto.outer.TaskDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pan.artem.conspecterrepo.exception.ResourceNotFoundException;
import pan.artem.conspecterrepo.domain.SolutionVerifier;
import pan.artem.conspecterrepo.domain.SolutionVerifierImpl;
import pan.artem.conspecterrepo.entity.User;
import pan.artem.conspecterrepo.repository.TaskRepository;

import java.util.Random;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final SolutionVerifier verifier = new SolutionVerifierImpl();

    @Override
    @Transactional
    public TaskDto getTask(String username, String repoName, String conspectName) {
        var tasks = taskRepository.findUnsolvedTasks(username, repoName, conspectName);
        if (tasks.isEmpty()) {
            return null;
        }

        int index = new Random().nextInt(tasks.size());
        var entity = tasks.get(index);
        User user = userService.findOrCreate(username);
        user.setCurrentTask(entity);
        userService.save(user);

        return new TaskDto(repoName, conspectName, entity.getText());
    }

    @Override
    public TaskDto getCurrentTask(String username) {
        User user = userService.findOrCreate(username);

        var currentTask = user.getCurrentTask();
        if (currentTask == null) {
            return null;
        }

        var conspect = currentTask.getConspect();
        String conspectName = conspect.getName();
        String repositoryName = conspect.getRepository().getName();

        return new TaskDto(repositoryName, conspectName, currentTask.getText());
    }

    @Transactional
    @Override
    public SolvedTask checkSolution(String username, String solution) {
        User user = userService.findOrCreate(username);
        var currentTask = user.getCurrentTask();

        if (currentTask == null) {
            throw new ResourceNotFoundException(
                    "Unable to check solution because user " + username
                            + " doesn't have current task."
            );
        }

        var solvedTask = verifier.verify(currentTask.getAnswer(), solution);
        if (solvedTask.status()) {
            taskRepository.addUserSolved(username, currentTask.getId());
        }
        user.setCurrentTask(null);
        userService.save(user);

        return solvedTask;
    }
}
