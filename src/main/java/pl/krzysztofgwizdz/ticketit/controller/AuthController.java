package pl.krzysztofgwizdz.ticketit.controller;

import pl.krzysztofgwizdz.ticketit.entity.User;
import pl.krzysztofgwizdz.ticketit.dto.UserDto;
import pl.krzysztofgwizdz.ticketit.error.UserAlreadyExistsException;
import pl.krzysztofgwizdz.ticketit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {

    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @GetMapping("/signUp")
    public String showSignUpForm(Model model) {
        UserDto newUser = new UserDto();
        model.addAttribute("user", newUser);
        return "auth/signUpForm";
    }

    @PostMapping("/signUp")
    public String signUp(
            @ModelAttribute("user") @Valid UserDto userDto,
            BindingResult result
    ) {
        User registredUser = new User();
        if (!result.hasErrors()){
            registredUser = createUserAccount(userDto);
        }
        if (registredUser == null){
            //result.reject("Istnieje już użytkownik o podanym loginie lub emailu.");
        }
        if (result.hasErrors()){
            return "auth/signUpForm";
        }
        return "auth/confirmation";
    }

    private User createUserAccount(UserDto userDto) {
        User registeredUser = null;
        try {
            registeredUser = userService.signUpNewUser(userDto);
        } catch (UserAlreadyExistsException exc){
            return null;
        }
        return registeredUser;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
