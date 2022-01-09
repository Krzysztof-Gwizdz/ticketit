package pl.krzysztofgwizdz.ticketit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.krzysztofgwizdz.ticketit.dto.ProjectDto;
import pl.krzysztofgwizdz.ticketit.entity.Project;
import pl.krzysztofgwizdz.ticketit.entity.ProjectUserRoleLink;
import pl.krzysztofgwizdz.ticketit.entity.Ticket;
import pl.krzysztofgwizdz.ticketit.service.ProjectService;
import pl.krzysztofgwizdz.ticketit.service.TicketService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;
    private TicketService ticketService;

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

    @GetMapping("/create")
    public String getNewProjectForm(Model model) {
        ProjectDto projectDto = new ProjectDto();
        model.addAttribute("project", projectDto);
        return "project/form";
    }

    @PostMapping("/create")
    public String createNewProject(
            @ModelAttribute("project") @Valid ProjectDto projectDto,
            BindingResult result,
            Principal principal
    ) {
        if (result.hasErrors()) {
            return "project/form";
        }
        //Create new project with service
        Project savedProject = projectService.saveProjectWithUserAndRole(principal.getName(), projectDto);
        if (savedProject == null) {
            return "project/form";
        }
        return "redirect:/project";
    }

    @GetMapping("/{projectAcronym}")
    public String projectPage(
            @PathVariable("projectAcronym") String projectAcronym,
            Model model
    ) {
        Project project = projectService.getProjectByAcronym(projectAcronym);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        //TODO 403 error when not authorized
        Set<Ticket> tickets = ticketService.findTicketsByProject(project.getProjectId());
        Set<Ticket> openTickets = tickets.stream().filter(t -> t.getStatus().getId() == 1).collect(Collectors.toSet());
        Set<Ticket> inProgressTickets = tickets.stream().filter(t -> t.getStatus().getId() == 2).collect(Collectors.toSet());
        model.addAttribute("project", project);
        model.addAttribute("openTickets", openTickets);
        model.addAttribute("inProgressTickets", inProgressTickets);
        return "project/projectPage";
    }

    @GetMapping("/{projectAcronym}/settings")
    public String projectSettings(
            @PathVariable("projectAcronym") String projectAcronym,
            Model model,
            Principal principal
    ) {
        Project project = projectService.getProjectByAcronym(projectAcronym);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        ProjectUserRoleLink memberFound = project.getProjectUserRoleLink().stream().filter(projectUserRoleLink -> projectUserRoleLink.getUser().getUsername().equals(principal.getName())).findAny().get();
        if(memberFound == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        model.addAttribute("project", project);
        return "project/settings";
    }

    @PostMapping("/update")
    public String projectSettingsPost(
            @ModelAttribute("project") Project project,
            @ModelAttribute("projectUsers") HashSet<ProjectUserRoleLink> projectUsers,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "project/settings";
        }
        Project savedProject = projectService.updateProject(project);
        if (savedProject == null) {
            return "project/form";
        }
        return "redirect:/project";
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}
