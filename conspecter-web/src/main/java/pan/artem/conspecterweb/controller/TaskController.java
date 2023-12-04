package pan.artem.conspecterweb.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pan.artem.conspecterweb.dto.Task;
import pan.artem.conspecterweb.service.RepositoryServiceClient;

import java.security.Principal;

@AllArgsConstructor
@Controller
public class TaskController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RepositoryServiceClient repositoryServiceClient;

    @GetMapping("/{repoName}/{conspectName}")
    public ModelAndView showTask(
            Principal principal,
            @PathVariable("repoName") String repoName,
            @PathVariable("conspectName") String conspectName
    ) {
        ModelAndView model = new ModelAndView();
        String username = principal.getName();
        var currentTask = repositoryServiceClient.getCurrentTask(username);
        Task task = currentTask.orElseGet(
                () -> repositoryServiceClient.getTask(
                        username, repoName, conspectName
                )
        );
        if (task == null) {
            model.setStatus(HttpStatus.NOT_FOUND);
            model.setViewName("no_task_found");
            return model;
        }
        model.addObject("task", task);
        model.setViewName("task");
        return model;
    }

    @PostMapping("/*/*")
    public ModelAndView showResult(
            Principal principal,
            @RequestParam("solution") String solution
    ) {
        ModelAndView model = new ModelAndView();
        String username = principal.getName();
        logger.debug("username: {}, solution: {}", username, solution);

        var currentTask = repositoryServiceClient.getCurrentTask(username);
        if (currentTask.isEmpty()) {
            model.setStatus(HttpStatus.NOT_FOUND);
            model.setViewName("no_task_found");
            return model;
        }
        var solvedTask = repositoryServiceClient.sendSolution(username, solution);

        model.addObject("task", currentTask.get());
        model.addObject("solution", solution);
        model.addObject("solvedTask", solvedTask);

        String result = solvedTask.status() ? "Success!" : "Failure";
        model.addObject("result", result);

        model.setViewName("task_result");
        return model;
    }
}
