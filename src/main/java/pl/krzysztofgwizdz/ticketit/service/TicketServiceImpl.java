package pl.krzysztofgwizdz.ticketit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krzysztofgwizdz.ticketit.dao.TicketRepository;
import pl.krzysztofgwizdz.ticketit.entity.Ticket;
import pl.krzysztofgwizdz.ticketit.entity.TicketComment;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Override
    @Transactional
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAllTickets();
    }

    @Override
    @Transactional
    public Ticket findTicketById(Long id) {
        return ticketRepository.findTicketById(id);
    }

    @Override
    @Transactional
    public void saveTicket(Ticket ticket) {
        ticketRepository.saveTicket(ticket);
    }

    @Override
    @Transactional
    public void deleteTicketById(Long id) {
        ticketRepository.deleteTicketById(id);
    }

    @Override
    @Transactional
    public Ticket findTicketWithCommentsById(long ticketId) {
        return ticketRepository.findTicketWithCommentsById(ticketId);
    }

    @Override
    @Transactional
    public void addCommentToTicketById(long ticketId, TicketComment comment) {
        ticketRepository.addCommentToTicketById(ticketId, comment);
    }
}
