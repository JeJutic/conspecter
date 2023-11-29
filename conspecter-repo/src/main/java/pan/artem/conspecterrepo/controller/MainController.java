package pan.artem.conspecterrepo.controller;

import dto.ConspectDto;
import dto.RepositoryDto;
import dto.SolvedTask;
import dto.TaskDto;
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

    @Operation(
            summary = "Retrieves current weather in specified location",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Specified location was not found"
                    )
            }
    )
    @GetMapping("/")
    public ResponseEntity<List<RepositoryDto>> getRepositories() {
        var repositories = repositoryService.findAll();
        return ResponseEntity.ok(repositories);
    }

    @GetMapping("/{repoName}")
    public ResponseEntity<List<ConspectDto>> getConspects(
            @PathVariable("repoName") String repoName,
            @RequestParam("username") String username
    ) {
        var conspects = conspectService.findAllByRepoName(username, repoName);
        return ResponseEntity.ok(conspects);
    }

    @GetMapping("/{repoName}/{conspectName}")
    public ResponseEntity<TaskDto> getTask(
            @PathVariable("repoName") String repoName,
            @PathVariable("conspectName") String conspectName,
            @RequestParam("username") String username
    ) {
        var task = taskService.getTask(username, repoName, conspectName);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/")
    public ResponseEntity<SolvedTask> sendSolution(
            @RequestParam("username") String username,
            @RequestParam("solution") String solution
    ) {
        logger.info("username: {}, solution: {}", username, solution);

        var solvedTask = taskService.checkSolution(username, solution);
        return ResponseEntity.ok(solvedTask);
    }

    @GetMapping("/current")
    public ResponseEntity<TaskDto> getCurrentTask(
            @RequestParam("username") String username
    ) {
        var task = taskService.getCurrentTask(username);
        return ResponseEntity.ok(task);
    }
}
