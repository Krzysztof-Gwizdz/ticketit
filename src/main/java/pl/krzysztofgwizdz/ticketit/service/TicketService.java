package pl.krzysztofgwizdz.ticketit.service;

import pl.krzysztofgwizdz.ticketit.entity.Ticket;
import pl.krzysztofgwizdz.ticketit.entity.TicketComment;

import java.util.List;

public interface TicketService {

    List<Ticket> findAllTickets();

    Ticket findTicketById(Long id);

    void saveTicket(Ticket ticket);

    void deleteTicketById(Long id);

    Ticket findTicketWithCommentsById(long ticketId);

    void addCommentToTicketById(long ticketId, TicketComment comment);
}
