package pl.krzysztofgwizdz.ticketit.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krzysztofgwizdz.ticketit.entity.Authority;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class AuthorityRepositoryImpl implements AuthorityRepository {

    private EntityManager entityManager;

    @Autowired
    public AuthorityRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Authority> getAuthorities() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Authority> criteriaQuery = criteriaBuilder.createQuery(Authority.class);
        criteriaQuery.from(Authority.class);
        List<Authority> authorityList = session.createQuery(criteriaQuery).getResultList();
        return authorityList;
    }

    @Override
    public Authority getAuthorityById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Authority authority = session.byId(Authority.class).load(id);
        return authority;
    }

    @Override
    public Authority getAuthorityByName(String name) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from Authority where authorityName = :authorityName");
        query.setParameter("authorityName", name);
        List<Authority> authorities = query.getResultList();
        if (authorities.size() == 1) {
            return authorities.get(0);
        }
        return null;
    }

    @Override
    public void saveAuthority(String authorityName) {
        Session session = entityManager.unwrap(Session.class);
        Authority authority = new Authority();
        authority.setAuthorityName(authorityName);
        session.save(authorityName);
    }
}
