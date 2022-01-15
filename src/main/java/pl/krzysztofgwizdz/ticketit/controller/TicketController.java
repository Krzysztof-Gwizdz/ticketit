package pl.krzysztofgwizdz.ticketit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.krzysztofgwizdz.ticketit.dto.TicketDto;
import pl.krzysztofgwizdz.ticketit.entity.*;
import pl.krzysztofgwizdz.ticketit.service.ProjectService;
import pl.krzysztofgwizdz.ticketit.service.TicketService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Controller
@RequestMapping("/project/{projectAcronym}/ticket")
public class TicketController {

    private TicketService ticketService;
    private ProjectService projectService;

    @GetMapping
    public String getTicketList(Model model) {
        List<Ticket> tickets = ticketService.findAllTickets();
        model.addAttribute("tickets", tickets);
        return "ticketOverview";
    }

    @GetMapping("/create")
    public String showAddTicketForm(
            @PathVariable("projectAcronym") String projectAcronym,
            Principal principal,
            Model model
    ) {
        TicketDto ticket = new TicketDto();
        Project project = projectService.getProjectByAcronym(projectAcronym);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        Set<ProjectUserRoleLink> projectMembers = projectService.getProjectUserRoleLinksByUser(principal.getName());
        try {
            ProjectUserRoleLink membership = projectMembers.stream().filter(m -> m.getProject().equals(project)).findAny().get();
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        model.addAttribute("project", project);
        model.addAttribute("ticket", ticket);
        return "ticket/createTicketForm";
    }

    @PostMapping("/create")
    public String saveTicket(
            @PathVariable("projectAcronym") String projectAcronym,
            @ModelAttribute("ticket") @Valid TicketDto ticket,
            BindingResult result,
            Principal principal,
            Model model
    ) {
        Project project = projectService.getProjectByAcronym(projectAcronym);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        Set<ProjectUserRoleLink> projectMembers = projectService.getProjectUserRoleLinksByUser(principal.getName());
        try {
            ProjectUserRoleLink membership = projectMembers.stream().filter(m -> m.getProject().equals(project)).findAny().get();
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        if (result.hasErrors()) {
            model.addAttribute("project", project);
            return "ticket/createTicketForm";
        }
        ticketService.saveTicket(ticket, principal.getName(), projectAcronym);
        return "redirect:/project/" + projectAcronym;
    }

    @RequestMapping("/{ticketId}")
    public String showTicketDetails(
            @PathVariable("projectAcronym") String projectAcronym,
            @PathVariable("ticketId") long ticketId,
            Principal principal,
            Model model
    ) {
        Ticket ticket = ticketService.findTicketWithCommentsById(ticketId);
        Project project = projectService.getProjectByAcronym(projectAcronym);
        if (project == null || !project.equals(ticket.getProject())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        Set<ProjectUserRoleLink> projectMembers = projectService.getProjectUserRoleLinksByUser(principal.getName());
        try {
            ProjectUserRoleLink membership = projectMembers.stream().filter(m -> m.getProject().equals(project)).findAny().get();
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        TicketComment ticketComment = new TicketComment();
        model.addAttribute("projectAcronym", projectAcronym);
        model.addAttribute("ticket", ticket);
        model.addAttribute("comment", ticketComment);
        return "ticket/ticketDetails";
    }

    @GetMapping("{ticketId}/update")
    public String showUpdateTicketForm(
            @PathVariable("projectAcronym") String projectAcronym,
            @PathVariable("ticketId") long ticketId,
            Principal principal,
            Model model
    ) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        Project project = projectService.getProjectByAcronym(projectAcronym);
        if (project == null || !project.equals(ticket.getProject())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        Set<ProjectUserRoleLink> projectMembers = projectService.getProjectUserRoleLinksByUser(principal.getName());
        try {
            ProjectUserRoleLink membership = projectMembers.stream().filter(m -> m.getProject().equals(project)).findAny().get();
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        Set<TicketStatus> ticketStatuses = ticketService.findAllTicketStatuses();
        TicketDto ticketDto = new TicketDto(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getContent(),
                ticket.getStatus().getId()
        );
        model.addAttribute("projectAcronym", projectAcronym);
        model.addAttribute("ticket", ticketDto);
        model.addAttribute("ticketStatuses", ticketStatuses);
        return "ticket/updateTicket";
    }

    @PostMapping("{ticketId}/update")
    public String udateTicket(
            @PathVariable("projectAcronym") String projectAcronym,
            @PathVariable("ticketId") long ticketId,
            @ModelAttribute("ticket") TicketDto ticketDto,
            BindingResult bindingResult,
            Model model,
            Principal principal
    ) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        Project project = projectService.getProjectByAcronym(projectAcronym);
        if (project == null || !project.equals(ticket.getProject())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        Set<ProjectUserRoleLink> projectMembers = projectService.getProjectUserRoleLinksByUser(principal.getName());
        try {
            ProjectUserRoleLink membership = projectMembers.stream().filter(m -> m.getProject().equals(project)).findAny().get();
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        if (bindingResult.hasErrors()) {
            Set<TicketStatus> ticketStatuses = ticketService.findAllTicketStatuses();
            model.addAttribute("projectAcronym", projectAcronym);
            model.addAttribute("ticket", ticketDto);
            model.addAttribute("ticketStatuses", ticketStatuses);
            return "ticket/updateTicket";
        }
        ticketService.updateTicket(ticketDto);

        StringBuilder url = new StringBuilder();
        url.append("redirect:/project/");
        url.append(projectAcronym);
        url.append("/ticket/");
        url.append(ticketId);
        return url.toString();
    }

    @RequestMapping("/delete")
    public String deleteTicket(@RequestParam("ticketId") long ticketId) {
        ticketService.deleteTicketById(ticketId);
        return "redirect:/ticket/list";
    }

    @PostMapping("{ticketId}/add-comment")
    public String addCommentToTicket(
            @PathVariable("projectAcronym") String projectAcronym,
            @PathVariable("ticketId") long ticketId,
            @ModelAttribute("comment") @Valid TicketComment comment,
            BindingResult result,
            Principal principal,
            Model model
    ) {
        Project project = projectService.getProjectByAcronym(projectAcronym);
        Ticket ticket = ticketService.findTicketWithCommentsById(ticketId);
        if (project == null || ticket == null || !project.equals(ticket.getProject())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        Set<ProjectUserRoleLink> projectMembers = projectService.getProjectUserRoleLinksByUser(principal.getName());
        try {
            ProjectUserRoleLink membership = projectMembers.stream().filter(m -> m.getProject().equals(project)).findAny().get();
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        model.addAttribute("ticket", ticket);
        model.addAttribute("projectAcronym", projectAcronym);
        if (result.hasErrors()) {
            return "ticket/ticketDetails";
        }
        ticketService.addCommentToTicketById(ticketId, comment, principal.getName());
        StringBuilder url = new StringBuilder();
        url.append("redirect:/project/");
        url.append(projectAcronym);
        url.append("/ticket/");
        url.append(ticketId);
        return url.toString();
    }

    @GetMapping("{ticketId}/comment/{commentId}/delete")
    public String deleteTicketComment(
            @PathVariable("projectAcronym") String projectAcronym,
            @PathVariable("ticketId") long ticketId,
            @PathVariable("commentId") long commentId,
            Principal principal
    ) {
        Project project = projectService.getProjectByAcronym(projectAcronym);
        Ticket ticket = ticketService.findTicketWithCommentsById(ticketId);
        if (project == null || ticket == null || !project.equals(ticket.getProject())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        Set<ProjectUserRoleLink> projectMembers = projectService.getProjectUserRoleLinksByUser(principal.getName());
        try {
            ProjectUserRoleLink membership = projectMembers.stream().filter(m -> m.getProject().equals(project)).findAny().get();
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        TicketComment commentToDelete = ticket.getCommentList().stream().filter(comment -> comment.getId() == commentId).findFirst().get();
        if (commentToDelete == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        if (!principal.getName().equals(commentToDelete.getUser().getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        ticketService.deleteComment(commentId);
        StringBuilder url = new StringBuilder();
        url.append("redirect:/project/");
        url.append(projectAcronym);
        url.append("/ticket/");
        url.append(ticketId);
        return url.toString();
    }

    @GetMapping("{ticketId}/comment/{commentId}/update")
    public String updateTicketCommentForm(
            @PathVariable("projectAcronym") String projectAcronym,
            @PathVariable("ticketId") long ticketId,
            @PathVariable("commentId") long commentId,
            Model model,
            Principal principal
    ) {
        Project project = projectService.getProjectByAcronym(projectAcronym);
        Ticket ticket = ticketService.findTicketWithCommentsById(ticketId);
        if (project == null || ticket == null || !project.equals(ticket.getProject())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        Set<ProjectUserRoleLink> projectMembers = projectService.getProjectUserRoleLinksByUser(principal.getName());
        try {
            ProjectUserRoleLink membership = projectMembers.stream().filter(m -> m.getProject().equals(project)).findAny().get();
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        TicketComment commentToUpdate = ticket.getCommentList().stream().filter(comment -> comment.getId() == commentId).findFirst().get();
        if (commentToUpdate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        if (!principal.getName().equals(commentToUpdate.getUser().getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        model.addAttribute("projectAcronym", projectAcronym);
        model.addAttribute("ticketId", ticket.getId());
        model.addAttribute("comment", commentToUpdate);
        return "ticket/commentUpdateForm";
    }

    @PostMapping("{ticketId}/comment/{commentId}/update")
    public String updateTicketComment(
            @PathVariable("projectAcronym") String projectAcronym,
            @PathVariable("ticketId") long ticketId,
            @PathVariable("commentId") long commentId,
            @ModelAttribute("comment") TicketComment comment,
            Principal principal
    ) {
        Project project = projectService.getProjectByAcronym(projectAcronym);
        Ticket ticket = ticketService.findTicketWithCommentsById(ticketId);
        if (project == null || ticket == null || !project.equals(ticket.getProject())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        Set<ProjectUserRoleLink> projectMembers = projectService.getProjectUserRoleLinksByUser(principal.getName());
        try {
            ProjectUserRoleLink membership = projectMembers.stream().filter(m -> m.getProject().equals(project)).findAny().get();
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        TicketComment commentToUpdate = ticket.getCommentList().stream().filter(commentIter -> commentIter.getId() == commentId).findFirst().get();
        if (commentToUpdate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        if (!principal.getName().equals(commentToUpdate.getUser().getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "application.error.403");
        }
        commentToUpdate.setContent(comment.getContent());
        ticketService.updateComment(commentToUpdate);
        StringBuilder url = new StringBuilder();
        url.append("redirect:/project/");
        url.append(projectAcronym);
        url.append("/ticket/");
        url.append(ticketId);
        return url.toString();
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
}
