package pl.krzysztofgwizdz.ticketit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    @RequestMapping
    public String overview() {
        return "admin/adminOverview";
    }

    public String userManagementPage() {
        return "admin/manageUsers";
    }
}
