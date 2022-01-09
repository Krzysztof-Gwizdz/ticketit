package pl.krzysztofgwizdz.ticketit.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krzysztofgwizdz.ticketit.entity.Ticket;
import pl.krzysztofgwizdz.ticketit.entity.TicketComment;
import pl.krzysztofgwizdz.ticketit.entity.TicketStatus;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    private EntityManager entityManager;

    @Autowired
    public TicketRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Ticket> findAllTickets() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Ticket> criteria = criteriaBuilder.createQuery(Ticket.class);
        criteria.from(Ticket.class);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public Ticket findTicketById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.byId(Ticket.class).load(id);
    }

    @Override
    public Ticket findTicketWithCommentsById(long ticketId) {
        Session session = entityManager.unwrap(Session.class);
        Ticket ticket = session.byId(Ticket.class).load(ticketId);
        Hibernate.initialize(ticket.getCommentList());
        return ticket;
    }

    @Override
    public Set<Ticket> findTicketsByProject(long projectId) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("FROM Ticket t WHERE t.project.projectId=:projectId");
        query.setParameter("projectId", projectId);
        List<Ticket> result = query.getResultList();
        Set<Ticket> tickets = new HashSet<>(result);
        return tickets;
    }

    @Override
    public void saveTicket(Ticket ticket) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(ticket);
    }

    @Override
    public void deleteTicketById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Ticket ticketToDelete = session.byId(Ticket.class).load(id);
        session.delete(ticketToDelete);
    }

    @Override
    public void addCommentToTicketById(TicketComment comment) {
        Session session = entityManager.unwrap(Session.class);
        Ticket ticket = session.byId(Ticket.class).load(comment.getTicket().getId());
        Hibernate.initialize(ticket.getCommentList());
        ticket.addComment(comment);
        session.update(ticket);
    }

    @Override
    public void updateTicketComment(TicketComment comment) {
        Session session = entityManager.unwrap(Session.class);
        session.update(comment);
    }

    @Override
    public void deleteComment(long commentId) {
        Session session = entityManager.unwrap(Session.class);
        session.createQuery("delete from TicketComment where id= :id").setParameter("id", commentId).executeUpdate();
    }

    @Override
    public TicketStatus findTicketStatusById(Integer statusId) {
        Session session = entityManager.unwrap(Session.class);
        TicketStatus ticketStatus = session.byId(TicketStatus.class).load(statusId);
        return ticketStatus;
    }
}
