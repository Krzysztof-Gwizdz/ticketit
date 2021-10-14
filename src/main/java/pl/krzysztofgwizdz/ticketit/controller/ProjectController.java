package pl.krzysztofgwizdz.ticketit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.krzysztofgwizdz.ticketit.entity.Project;
import pl.krzysztofgwizdz.ticketit.entity.ProjectUserRoleLink;
import pl.krzysztofgwizdz.ticketit.service.ProjectService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController() {
    }

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String getProjectList(Model model, Principal principal) {
        Set<ProjectUserRoleLink> projectList = projectService.getProjectUserRoleLinksByUser(principal.getName());
        model.addAttribute("projectsLinks", projectList);
        return "project/overview";
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
}
