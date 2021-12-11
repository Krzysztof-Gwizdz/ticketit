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
import pl.krzysztofgwizdz.ticketit.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/project/{projectAcronym}/ticket")
public class TicketController {

    Logger logger = LoggerFactory.getLogger(TicketController.class);

    private TicketService ticketService;
    private ProjectService projectService;
    private UserService userService;

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

    @GetMapping("/update")
    public String showUpdateTicketForm(@RequestParam("ticketId") long ticketId, Model model) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        model.addAttribute("ticket", ticket);
        return "ticketForm";
    }

    @PostMapping("/create")
    public String saveTicket(
            @PathVariable("projectAcronym") String projectAcronym,
            @ModelAttribute("ticket") @Valid TicketDto ticket,
            BindingResult result,
            Principal principal,
            Model model
    ) {
        if(result.hasErrors()) {
            Project project = projectService.getProjectByAcronym(projectAcronym);
            model.addAttribute("project", project);
            return "ticket/createTicketForm";
        }
        logger.info("Ticket: " + ticket);
        logger.info("Project acronym: " + projectAcronym);
        logger.info("Username: " + principal.getName());
        return "redirect:/project/" + projectAcronym;
    }

    @RequestMapping("/delete")
    public String deleteTicket(@RequestParam("ticketId") long ticketId) {
        ticketService.deleteTicketById(ticketId);
        return "redirect:/ticket/list";
    }

    @RequestMapping("/details")
    public String showTicketDetails(@RequestParam("ticketId") long ticketId, Model model) {
        Ticket ticket = ticketService.findTicketWithCommentsById(ticketId);
        TicketComment ticketComment = new TicketComment();
        model.addAttribute("ticket", ticket);
        model.addAttribute("comment", ticketComment);
        return "ticketDetails";
    }

    @PostMapping("/addComment")
    public String addCommentToTicket(@RequestParam("ticketId") long ticketId,
                                     @ModelAttribute("comment") TicketComment comment) {
        ticketService.addCommentToTicketById(ticketId, comment);
        StringBuilder sb = new StringBuilder();
        sb.append("redirect:/ticket/details?ticketId=");
        sb.append(ticketId);
        return sb.toString();
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
