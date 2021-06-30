package pl.krzysztofgwizdz.ticketit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krzysztofgwizdz.ticketit.dto.OrganizationDTO;
import pl.krzysztofgwizdz.ticketit.entity.Organization;
import pl.krzysztofgwizdz.ticketit.service.OrganizationsService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationsService organizationsService;

    @Autowired
    public OrganizationController(OrganizationsService organizationsService) {
        this.organizationsService = organizationsService;
    }

    @GetMapping
    public String organizationsOverwiew(Model model) {
        List<Organization> organizations = organizationsService.findAll();
        model.addAttribute("organizations", organizations);
        return "organizations/overview";
    }

    @GetMapping("/{name}")
    public String organizationMainView(@PathVariable("name") String organizationName, Model model) {
        Organization organization = organizationsService.findByShortname(organizationName);
        model.addAttribute("organization", organization);
        return "organizations/organizationDetails";
    }

    @GetMapping("/create")
    public String createOrganiztion(Model model) {
        OrganizationDTO newOraganization = new OrganizationDTO();
        model.addAttribute("organization", newOraganization);
        return "organizations/organizationForm";
    }

    @PostMapping("/create")
    public String createOrganization(
            @ModelAttribute("organization") @Valid OrganizationDTO organizationDTO,
            BindingResult result,
            Principal principal
    ) {
        if (!result.hasErrors() && organizationsService.saveOrganization(organizationDTO, principal.getName()) != null) {
            return "organizations/overview";
        }
        return "organizations/organizationForm";
    }
}
