package pl.krzysztofgwizdz.ticketit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krzysztofgwizdz.ticketit.dao.ProjectRepository;
import pl.krzysztofgwizdz.ticketit.dao.TicketRepository;
import pl.krzysztofgwizdz.ticketit.dao.TicketStatusRepository;
import pl.krzysztofgwizdz.ticketit.dao.UserRepository;
import pl.krzysztofgwizdz.ticketit.dto.TicketDto;
import pl.krzysztofgwizdz.ticketit.entity.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class TicketServiceImpl implements TicketService {

    TicketRepository ticketRepository;
    TicketStatusRepository ticketStatusRepository;
    UserRepository userRepository;
    ProjectRepository projectRepository;

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
    public Ticket findTicketWithCommentsById(long ticketId) {
        return ticketRepository.findTicketWithCommentsById(ticketId);
    }

    @Override
    @Transactional
    public Set<Ticket> findTicketsByProject(long projectId) {
        return ticketRepository.findTicketsByProject(projectId);
    }

    @Override
    @Transactional
    public void saveTicket(TicketDto ticketDto, String username, String projectAcronym) {
        Ticket newTicket = new Ticket();
        User ticketAuthor = userRepository.findUserByUsername(username);
        Project baseProject = projectRepository.findByAcronym(projectAcronym);
        TicketStatus ticketStatus = ticketStatusRepository.findTicketStatusById(1);
        newTicket.setTitle(ticketDto.getTitle());
        newTicket.setContent(ticketDto.getContent());
        newTicket.setCreationDate(new Date());
        newTicket.setModificationDate(new Date());
        newTicket.setAuthor(ticketAuthor);
        newTicket.setProject(baseProject);
        newTicket.setStatus(ticketStatus);
        ticketRepository.saveTicket(newTicket);
    }

    @Override
    @Transactional
    public void updateTicket(TicketDto ticketDto) {
        Ticket ticket = ticketRepository.findTicketById(ticketDto.getTicketId());
        TicketStatus ticketStatus = ticketStatusRepository.findTicketStatusById(ticketDto.getTicketStatusId());
        ticket.setTitle(ticketDto.getTitle());
        ticket.setContent(ticketDto.getContent());
        ticket.setModificationDate(new Date());
        ticket.setStatus(ticketStatus);
        ticketRepository.saveTicket(ticket);
    }

    @Override
    @Transactional
    public void deleteTicketById(Long id) {
        ticketRepository.deleteTicketById(id);
    }

    @Override
    @Transactional
    public void addCommentToTicketById(long ticketId, TicketComment comment, String username) {
        Ticket ticket = ticketRepository.findTicketById(ticketId);
        User user = userRepository.findUserByUsername(username);
        if (ticket != null && user != null) {
            comment.setTicket(ticket);
            comment.setUser(user);
            ticketRepository.addCommentToTicketById(comment);
        }
    }

    @Override
    @Transactional
    public void updateComment(TicketComment comment) {
        ticketRepository.updateTicketComment(comment);
    }

    @Override
    @Transactional
    public Set<TicketStatus> findAllTicketStatuses() {
        return ticketStatusRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteComment(long commentId) {
        ticketRepository.deleteComment(commentId);
    }

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Autowired
    public void setTicketStatusRepository(TicketStatusRepository ticketStatusRepository) {
        this.ticketStatusRepository = ticketStatusRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
}
