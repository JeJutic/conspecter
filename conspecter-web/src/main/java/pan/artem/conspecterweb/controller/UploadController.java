package pan.artem.conspecterweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pan.artem.conspecterweb.service.RepositoryServiceClient;

@AllArgsConstructor
@RestController
@RequestMapping("/upload")
public class UploadController {

    private final RepositoryServiceClient repositoryServiceClient;

    @Operation(summary = "Retrieves metadata of all processed repositories")
    @PostMapping("/{author}/{repoName}")
    public void uploadRepository(
            @PathVariable("author") String author,
            @PathVariable("repoName") String repoName,
            @RequestParam("url") String repoUrl
    ) {
        repositoryServiceClient.uploadRepository(author, repoName, repoUrl);
    }
}
