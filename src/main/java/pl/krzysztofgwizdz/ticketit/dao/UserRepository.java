package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.User;

import java.util.List;

public interface UserRepository {
    User findUserByUsername(String username);

    List<User> findUserByUsernameOrEmail(String username, String email);

    User save(User user);
}
