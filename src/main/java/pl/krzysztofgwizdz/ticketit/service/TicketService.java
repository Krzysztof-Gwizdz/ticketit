package pl.krzysztofgwizdz.ticketit.service;

import pl.krzysztofgwizdz.ticketit.dto.TicketDto;
import pl.krzysztofgwizdz.ticketit.entity.Ticket;
import pl.krzysztofgwizdz.ticketit.entity.TicketComment;
import pl.krzysztofgwizdz.ticketit.entity.TicketStatus;

import java.util.List;
import java.util.Set;

public interface TicketService {

    List<Ticket> findAllTickets();

    Ticket findTicketById(Long id);

    Ticket findTicketWithCommentsById(long ticketId);

    Set<Ticket> findTicketsByProject(long projectId);

    void saveTicket(TicketDto ticket, String username, String projectAcronym);

    void updateTicket(TicketDto ticketDto);

    void deleteTicketById(Long id);

    void addCommentToTicketById(long ticketId, TicketComment comment, String username);

    void updateComment(TicketComment comment);

    void deleteComment(long commentId);

    Set<TicketStatus> findAllTicketStatuses();
}
