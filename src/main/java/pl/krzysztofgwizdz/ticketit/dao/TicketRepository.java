package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.Ticket;
import pl.krzysztofgwizdz.ticketit.entity.TicketComment;
import pl.krzysztofgwizdz.ticketit.entity.TicketStatus;

import java.util.List;
import java.util.Set;

public interface TicketRepository {

    List<Ticket> findAllTickets();

    Ticket findTicketById(Long id);

    Ticket findTicketWithCommentsById(long ticketId);

    Set<Ticket> findTicketsByProject(long projectId);

    void saveTicket(Ticket ticket);

    void deleteTicketById(Long id);

    void addCommentToTicketById(TicketComment comment);

    void updateTicketComment(TicketComment comment);

    void deleteComment(long commentId);
}
