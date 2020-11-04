package pl.krzysztofgwizdz.ticketit.controller;

import pl.krzysztofgwizdz.ticketit.entity.Ticket;
import pl.krzysztofgwizdz.ticketit.entity.TicketComment;
import pl.krzysztofgwizdz.ticketit.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    @RequestMapping({"", "/"})
    public String redirectToList() {
        return "redirect:/ticket/list";
    }

    @GetMapping("/list")
    public String getTicketList(Model model) {
        List<Ticket> tickets = ticketService.findAllTickets();
        model.addAttribute("tickets", tickets);
        return "ticketOverview";
    }

    @GetMapping("/add")
    public String showAddTicketForm(Model model, Principal principal) {
        Ticket ticket = new Ticket();
        ticket.setAuthor(principal.getName());
        model.addAttribute("ticket", ticket);
        return "ticketForm";
    }

    @GetMapping("/update")
    public String showUpdateTicketForm(@RequestParam("ticketId") long ticketId, Model model) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        model.addAttribute("ticket", ticket);
        return "ticketForm";
    }

    @PostMapping("/save")
    public String saveTicket(@ModelAttribute("ticket") Ticket ticket) {
        ticketService.saveTicket(ticket);
        return "redirect:/ticket/list";
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
                                     @ModelAttribute("comment") TicketComment comment,
                                     Principal principal){
        comment.setAuthor(principal.getName());
        ticketService.addCommentToTicketById(ticketId, comment);
        StringBuilder sb = new StringBuilder();
        sb.append("redirect:/ticket/details?ticketId=");
        sb.append(ticketId);
        return sb.toString();
    }

    @Autowired
    public void setTicketService(TicketService ticketService){
        this.ticketService = ticketService;
    }
}
