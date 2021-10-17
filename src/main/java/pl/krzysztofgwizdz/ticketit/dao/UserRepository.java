package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.User;

public interface UserRepository {

    User findUserById(int id);

    User findUserByUsername(String username);

    User findUserByUsernameOrEmail(String username, String email);

    User findUserByUsernameWithProjects(String username);

    User save(User user);
}
