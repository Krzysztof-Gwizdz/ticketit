package pl.krzysztofgwizdz.ticketit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krzysztofgwizdz.ticketit.dao.ProjectRepository;
import pl.krzysztofgwizdz.ticketit.dao.TicketRepository;
import pl.krzysztofgwizdz.ticketit.dao.UserRepository;
import pl.krzysztofgwizdz.ticketit.dto.TicketDto;
import pl.krzysztofgwizdz.ticketit.entity.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TicketServiceImpl implements TicketService {

    TicketRepository ticketRepository;
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
        TicketStatus ticketStatus = ticketRepository.findTicketStatusById(1);
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
    public void deleteTicketById(Long id) {
        ticketRepository.deleteTicketById(id);
    }

    @Override
    @Transactional
    public void addCommentToTicketById(long ticketId, TicketComment comment) {
        ticketRepository.addCommentToTicketById(ticketId, comment);
    }

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
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
