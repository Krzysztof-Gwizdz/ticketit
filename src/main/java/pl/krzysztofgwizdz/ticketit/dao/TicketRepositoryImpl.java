package pl.krzysztofgwizdz.ticketit.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krzysztofgwizdz.ticketit.entity.Ticket;
import pl.krzysztofgwizdz.ticketit.entity.TicketComment;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Timestamp;
import java.util.List;

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
    public void saveTicket(Ticket ticket) {
        Session session = entityManager.unwrap(Session.class);
        ticket.setModificationDate(new Timestamp(System.currentTimeMillis()));
        session.saveOrUpdate(ticket);
    }

    @Override
    public void deleteTicketById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Ticket ticketToDelete = session.byId(Ticket.class).load(id);
        session.delete(ticketToDelete);
    }

    @Override
    public Ticket findTicketWithCommentsById(long ticketId) {
        Session session = entityManager.unwrap(Session.class);
        Ticket ticket = session.byId(Ticket.class).load(ticketId);
        Hibernate.initialize(ticket.getCommentList());
        return ticket;
    }

    @Override
    public void addCommentToTicketById(long ticketId, TicketComment comment) {
        Session session = entityManager.unwrap(Session.class);
        comment.setCreationDate(new Timestamp(System.currentTimeMillis()));
        Ticket ticket = session.byId(Ticket.class).load(ticketId);
        Hibernate.initialize(ticket.getCommentList());
        ticket.addComment(comment);
        session.update(ticket);
    }
}
