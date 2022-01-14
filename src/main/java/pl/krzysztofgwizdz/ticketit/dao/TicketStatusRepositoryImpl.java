package pl.krzysztofgwizdz.ticketit.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krzysztofgwizdz.ticketit.entity.TicketStatus;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.HashSet;
import java.util.Set;

@Repository
public class TicketStatusRepositoryImpl implements TicketStatusRepository {

    private EntityManager entityManager;

    @Override
    public Set<TicketStatus> findAll() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TicketStatus> cq = cb.createQuery(TicketStatus.class);
        cq.from(TicketStatus.class);
        return new HashSet<>(session.createQuery(cq).getResultList());
    }

    @Override
    public TicketStatus findTicketStatusById(Integer statusId) {
        Session session = entityManager.unwrap(Session.class);
        TicketStatus ticketStatus = session.byId(TicketStatus.class).load(statusId);
        return ticketStatus;
    }

    public TicketStatusRepositoryImpl() {
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
