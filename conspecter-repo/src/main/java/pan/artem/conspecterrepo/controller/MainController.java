package pan.artem.conspecterrepo.controller;

import dto.outer.ConspectDto;
import dto.outer.RepositoryDto;
import dto.outer.SolvedTask;
import dto.outer.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pan.artem.conspecterrepo.service.ConspectService;
import pan.artem.conspecterrepo.service.RepositoryService;
import pan.artem.conspecterrepo.service.TaskService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RepositoryService repositoryService;
    private final ConspectService conspectService;
    private final TaskService taskService;

    @Operation(summary = "Retrieves metadata of all processed repositories")
    @GetMapping("/")
    public ResponseEntity<List<RepositoryDto>> getRepositories() {
        var repositories = repositoryService.findAll();
        return ResponseEntity.ok(repositories);
    }

    @Operation(summary = "Retrieves metadata relevant for the user " +
            "of all conspects in specified repository")
    @GetMapping("/{repoName}")
    public ResponseEntity<List<ConspectDto>> getConspects(
            @PathVariable("repoName") String repoName,
            @RequestParam("username") String username
    ) {
        var conspects = conspectService.findAllByRepoName(username, repoName);
        return ResponseEntity.ok(conspects);
    }

    @Operation(summary = "Finds suitable for the user task from specified conspect " +
            "or empty body if not found")
    @GetMapping("/{repoName}/{conspectName}")
    public ResponseEntity<TaskDto> getTask(
            @PathVariable("repoName") String repoName,
            @PathVariable("conspectName") String conspectName,
            @RequestParam("username") String username
    ) {
        var task = taskService.getTask(username, repoName, conspectName);
        return ResponseEntity.ok(task);
    }

    @Operation(
            summary = "Processes user's answer on theirs current task",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User has no current task"
                    )
            }
    )
    @PostMapping("/")
    public ResponseEntity<SolvedTask> sendSolution(
            @RequestParam("username") String username,
            @RequestParam("solution") String solution
    ) {
        logger.debug("username: {}, solution: {}", username, solution);

        var solvedTask = taskService.checkSolution(username, solution);
        return ResponseEntity.ok(solvedTask);
    }

    @Operation(summary = "Finds user's current task or empty body if not found")
    @GetMapping("/current")
    public ResponseEntity<TaskDto> getCurrentTask(
            @RequestParam("username") String username
    ) {
        var task = taskService.getCurrentTask(username);
        return ResponseEntity.ok(task);
    }
}
