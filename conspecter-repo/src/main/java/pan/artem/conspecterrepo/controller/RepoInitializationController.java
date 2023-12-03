package pan.artem.conspecterrepo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pan.artem.conspecterrepo.service.RepoInitializer;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class RepoInitializationController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RepoInitializer repoInitializer;

    @Operation(
            summary = "Clones or pulls specified repository and initializes its conspects",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Unable to initialize repository"
                    )
            }
    )
    @PostMapping("/init/{author}/{repoName}")
    public void generate(
            @PathVariable("author") String author,
            @PathVariable("repoName") String repoName,
            @RequestParam("url") String repoUrl
    ) {
        repoInitializer.initialize(author, repoName, repoUrl);
    }
}
