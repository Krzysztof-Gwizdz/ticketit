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
    public User findUserByUsername(String username) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(User.class, username);
    }

    @Override
    public List<User> findUserByUsernameOrEmail(String username, String email) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from User where username = :username or email = :email");
        query.setParameter("username", username);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public User save(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.save(user);
        return user;
    }
}
