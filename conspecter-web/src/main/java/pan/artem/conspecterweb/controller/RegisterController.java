package pan.artem.conspecterweb.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pan.artem.conspecterweb.service.RegisterService;

@AllArgsConstructor
@Controller
public class RegisterController {

    private final RegisterService registerService;

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @RateLimiter(name = "register")
    @PostMapping("/register")
    public String register(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            Model model
    ) {
        if (registerService.register(login, password)) {
            model.addAttribute(
                    "status",
                    "Successfully registered. Now you need to log in."
            );
        } else {
            model.addAttribute(
                    "status",
                    "Unable to register"
            );
        }
        return "register";
    }
}
