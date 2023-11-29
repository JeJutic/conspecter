package pan.artem.conspecterweb.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pan.artem.conspecterweb.service.RepositoryServiceClient;

import java.security.Principal;

@AllArgsConstructor
@Controller
public class ConspectsController {

    private final RepositoryServiceClient repositoryServiceClient;


    @GetMapping("/{repoName}")
    public String showMain(
            Principal principal,
            @PathVariable("repoName") String repoName,
            Model model
    ) {
        var conspects = repositoryServiceClient.getConspects(principal.getName(), repoName);
        model.addAttribute("repo", repoName);
        model.addAttribute("conspects", conspects);
        return "conspects";
    }
}
