package pl.krzysztofgwizdz.ticketit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krzysztofgwizdz.ticketit.dto.ProjectDto;
import pl.krzysztofgwizdz.ticketit.entity.Project;
import pl.krzysztofgwizdz.ticketit.entity.ProjectUserRoleLink;
import pl.krzysztofgwizdz.ticketit.service.ProjectService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
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

    @GetMapping("/{projectAcronym}/settings")
    public String projectSettings(
            @PathVariable("projectAcronym") String projectAcronym,
            Model model,
            Principal principal
    ) {
        Project project = projectService.getProjectByAcronym(projectAcronym);
        Set<ProjectUserRoleLink> projectUsers = projectService.getProjectUserRoleLinksByProject(project.getProjectId());
        model.addAttribute("project", project);
        model.addAttribute("projectUsers", projectUsers);
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
}
