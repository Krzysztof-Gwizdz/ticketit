package pl.krzysztofgwizdz.ticketit.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krzysztofgwizdz.ticketit.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAllUsers() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        query.from(User.class);
        return session.createQuery(query).getResultList();
    }

    @Override
    public User findUserById(long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(User.class, id);
    }

    @Override
    public User findUserByUsername(String username) {
        Session session = entityManager.unwrap(Session.class);
        return session.bySimpleNaturalId(User.class).load(username);
    }

    @Override
    public User findUserByUsernameOrEmail(String username, String email) {
        Session session = entityManager.unwrap(Session.class);
        User user = null;
        Query query = session.createQuery("from User where username = :username or email = :email");
        List<User> queryResult;
        query.setParameter("username", username);
        query.setParameter("email", email);
        queryResult = query.getResultList();
        if (queryResult.size() > 0) {
            user = queryResult.get(0);
        }
        return user;
    }

    @Override
    public User findUserByUsernameWithProjects(String username) {
        try {
            return entityManager.createQuery(
                    "select u " +
                            "from User u " +
                            "join fetch u.projectUserRoleLinks purl " +
                            "join fetch purl.project " +
                            "join fetch purl.role " +
                            "where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return findUserByUsername(username);
        }
    }

    @Override
    public User updateUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.update(user);
        return user;
    }

    @Override
    public User save(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(user);
        return user;
    }
}
