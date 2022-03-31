package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.User;

import java.util.List;

public interface UserRepository {

    List<User> findAllUsers();

    User findUserById(long id);

    User findUserByUsername(String username);

    User findUserByUsernameOrEmail(String username, String email);

    User findUserByUsernameWithProjects(String username);

    User updateUser(User user);

    User save(User user);
}
