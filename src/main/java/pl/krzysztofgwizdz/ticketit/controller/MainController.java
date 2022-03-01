package pl.krzysztofgwizdz.ticketit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.krzysztofgwizdz.ticketit.entity.User;
import pl.krzysztofgwizdz.ticketit.service.UserService;

@Controller
public class MainController {

    private UserService userService;

    @RequestMapping("/")
    public String homePage() {
        return "main/home";
    }

    @GetMapping("/user/{username}")
    public String usersPage(
            @PathVariable("username")String username,
            Model model
    ) {
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "main/userspage";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
