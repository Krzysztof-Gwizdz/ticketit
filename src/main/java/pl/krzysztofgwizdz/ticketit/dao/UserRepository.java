package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.User;

import java.util.List;

public interface UserRepository {

    List<User> findAllUsers();

    User findUserById(int id);

    User findUserByUsername(String username);

    User findUserByUsernameOrEmail(String username, String email);

    User findUserByUsernameWithProjects(String username);

    User save(User user);
}
