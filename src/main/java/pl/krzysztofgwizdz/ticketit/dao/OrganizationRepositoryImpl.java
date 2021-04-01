package pl.krzysztofgwizdz.ticketit.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krzysztofgwizdz.ticketit.entity.Organization;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private final EntityManager entityManager;

    @Autowired
    public OrganizationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Organization> findAllOrganizations() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);
        criteriaQuery.from(Organization.class);
        return session.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Organization findOrganization(long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.byId(Organization.class).load(id);
    }

    @Override
    public void saveOrganization(Organization organization) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(organization);
    }

    @Override
    public void deleteOrganization(Organization organization) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(organization);
    }
}
