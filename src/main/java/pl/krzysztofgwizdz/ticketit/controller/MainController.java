package pl.krzysztofgwizdz.ticketit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping
    public String homePage() {
        return "main/home";
    }

    @RequestMapping("/contact")
    public String contactPage() {
        return "main/contact";
    }

    @RequestMapping("/about")
    public String aboutPage(){
        return "main/about";
    }

}
