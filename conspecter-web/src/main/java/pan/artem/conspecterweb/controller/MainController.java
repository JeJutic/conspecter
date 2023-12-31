package pan.artem.conspecterweb.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
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
    public ModelAndView notFound() {
        ModelAndView model = new ModelAndView("no_task_found");
        model.setStatus(HttpStatus.NOT_FOUND);
        return model;
    }
}
