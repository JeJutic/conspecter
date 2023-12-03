package pan.artem.conspecterweb.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pan.artem.conspecterweb.dto.Task;
import pan.artem.conspecterweb.service.RepositoryServiceClient;

import java.security.Principal;

@AllArgsConstructor
@Controller
public class TaskController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RepositoryServiceClient repositoryServiceClient;

    @GetMapping("/{repoName}/{conspectName}")
    public String showTask(
            Principal principal,
            @PathVariable("repoName") String repoName,
            @PathVariable("conspectName") String conspectName,
            Model model
    ) {
        String username = principal.getName();
        var currentTask = repositoryServiceClient.getCurrentTask(username);
        Task task = currentTask.orElseGet(
                () -> repositoryServiceClient.getTask(
                        username, repoName, conspectName
                )
        );
        if (task == null) {
            return "no_task_found";
        }
        model.addAttribute("task", task);
        return "task";
    }

    @PostMapping("/*/*")
    public String showResult(
            Principal principal,
            @RequestParam("solution") String solution,
            Model model
    ) {
        String username = principal.getName();
        logger.debug("username: {}, solution: {}", username, solution);

        var currentTask = repositoryServiceClient.getCurrentTask(username);
        if (currentTask.isEmpty()) {
            return "no_task_found";
        }
        var solvedTask = repositoryServiceClient.sendSolution(username, solution);

        model.addAttribute("task", currentTask.get());
        model.addAttribute("solution", solution);
        model.addAttribute("solvedTask", solvedTask);

        String result = solvedTask.status() ? "Success!" : "Failure";
        model.addAttribute("result", result);

        return "task_result";
    }
}
