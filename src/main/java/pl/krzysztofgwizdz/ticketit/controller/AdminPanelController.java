package pl.krzysztofgwizdz.ticketit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.krzysztofgwizdz.ticketit.dto.UserListDto;
import pl.krzysztofgwizdz.ticketit.entity.User;
import pl.krzysztofgwizdz.ticketit.service.UserService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    private UserService userService;

    @RequestMapping
    public String overview() {
        return "admin/adminOverview";
    }

    @GetMapping("/users")
    public String userManagementPage(
            Model model
    ) {
        List<User> users = userService.findAllUsers();
        UserListDto usersDto = new UserListDto(users);
        model.addAttribute("usersDto", usersDto);
        return "admin/manageUsers";
    }

    @PostMapping("/users")
    public String userMangementSave(
            @ModelAttribute("usersDto") UserListDto usersDto
    ) {
        //TODO Prepare update users here, in service, and maybe repository
        return "redirect:/admin/users";
    }

    public AdminPanelController() {
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
