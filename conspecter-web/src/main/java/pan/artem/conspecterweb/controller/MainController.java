package pan.artem.conspecterweb.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pan.artem.conspecterweb.service.RepositoryServiceClient;

@AllArgsConstructor
@Controller
public class MainController {

    private final RepositoryServiceClient repositoryServiceClient;

    @GetMapping("/")
    public String showMain(Model model) {
        var repos = repositoryServiceClient.getRepositories();
        model.addAttribute("repos", repos);
        return "main";
    }

    @GetMapping("/**")
    public String notFound() {
        return "no_task_found";
    }
}
