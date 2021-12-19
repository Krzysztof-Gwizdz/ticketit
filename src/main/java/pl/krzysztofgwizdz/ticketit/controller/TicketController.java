package pl.krzysztofgwizdz.ticketit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.krzysztofgwizdz.ticketit.dto.TicketDto;
import pl.krzysztofgwizdz.ticketit.entity.Project;
import pl.krzysztofgwizdz.ticketit.entity.Ticket;
import pl.krzysztofgwizdz.ticketit.entity.TicketComment;
import pl.krzysztofgwizdz.ticketit.service.ProjectService;
import pl.krzysztofgwizdz.ticketit.service.TicketService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/project/{projectAcronym}/ticket")
public class TicketController {

    Logger logger = LoggerFactory.getLogger(TicketController.class);

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
            Model model,
            @PathVariable("projectAcronym") String projectAcronym
    ) {
        TicketDto ticket = new TicketDto();
        Project project = projectService.getProjectByAcronym(projectAcronym);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
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
        if (result.hasErrors()) {
            model.addAttribute("project", project);
            return "ticket/createTicketForm";
        }
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        logger.info("Ticket: " + ticket);
        logger.info("Project acronym: " + projectAcronym);
        logger.info("Username: " + principal.getName());
        ticketService.saveTicket(ticket, principal.getName(), projectAcronym);
        return "redirect:/project/" + projectAcronym;
    }

    @RequestMapping("/{ticketId}")
    public String showTicketDetails(
            @PathVariable("projectAcronym") String projectAcronym,
            @PathVariable("ticketId") long ticketId,
            Model model
    ) {
        Ticket ticket = ticketService.findTicketWithCommentsById(ticketId);
        Project project = projectService.getProjectByAcronym(projectAcronym);
        if (project == null || !project.equals(ticket.getProject())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        TicketComment ticketComment = new TicketComment();
        model.addAttribute("projectAcronym", projectAcronym);
        model.addAttribute("ticket", ticket);
        model.addAttribute("comment", ticketComment);
        return "ticket/ticketDetails";
    }

    @GetMapping("/update")
    public String showUpdateTicketForm(@RequestParam("ticketId") long ticketId, Model model) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        model.addAttribute("ticket", ticket);
        return "ticketForm";
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
        TicketComment commentToDelete = ticket.getCommentList().stream().filter(comment -> comment.getId() == commentId).findFirst().get();
        if (commentToDelete == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application.error.404");
        }
        if(!principal.getName().equals(commentToDelete.getUser().getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "application.error.403");
        }
        ticketService.deleteComment(commentId);
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
