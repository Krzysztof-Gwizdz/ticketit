package pl.krzysztofgwizdz.ticketit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.krzysztofgwizdz.ticketit.dto.PasswordDto;
import pl.krzysztofgwizdz.ticketit.dto.UserBasicDto;
import pl.krzysztofgwizdz.ticketit.entity.User;
import pl.krzysztofgwizdz.ticketit.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @RequestMapping
    public String userList(
            Model model
    ) {
        Collection<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{username}")
    public String usersPage(
            @PathVariable("username") String username,
            Model model
    ) {
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "user/userpage";
    }

    @GetMapping("/{username}/edit")
    public String updateUserForm(
            @PathVariable("username") String username,
            Principal principal,
            Model model
    ) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        if (!principal.getName().equals(username) && !principal.getName().equals(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        UserBasicDto userDto = new UserBasicDto(user.getUsername(), user.getFirstName(), user.getLastName());
        model.addAttribute("user", userDto);
        return "user/edit";
    }

    @PostMapping("/edit")
    public String updateUser(
            @ModelAttribute("user") UserBasicDto userDto,
            Principal principal
    ) {
        if (!principal.getName().equals(userDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        userService.updateUserNames(userDto);
        return "redirect:/user/" + userDto.getUsername();
    }

    @GetMapping("{username}/change-password")
    public String changePassForm(
            @PathVariable("username") String username,
            Model model,
            Principal principal
    ) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        if (!principal.getName().equals(username) && !principal.getName().equals(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setUsername(username);
        model.addAttribute("pass", passwordDto);
        return "user/changePassword";
    }

    @PostMapping("/change-password")
    public String changePass(
            @Valid @ModelAttribute("pass") PasswordDto passwordDto,
            BindingResult result,
            Principal principal
    ) {
        if (result.hasErrors()) {
            return "user/changePassword";
        }
        if (!principal.getName().equals(passwordDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        userService.updatePassword(passwordDto);
        return "redirect:/user/" + passwordDto.getUsername();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
