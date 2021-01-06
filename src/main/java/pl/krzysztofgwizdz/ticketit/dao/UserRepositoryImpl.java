package pl.krzysztofgwizdz.ticketit.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krzysztofgwizdz.ticketit.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findUserById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(User.class, id);
    }

    @Override
    public User findUserByUsername(String username) {
        Session session = entityManager.unwrap(Session.class);
        User user = null;
        Query query = session.createQuery("from User where username = :username");
        List<User> queryResult;
        query.setParameter("username", username);
        queryResult = query.getResultList();
        if (queryResult.size() > 0) {
            user = queryResult.get(0);
        }
        return user;
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
    public User save(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(user);
        return user;
    }
}
