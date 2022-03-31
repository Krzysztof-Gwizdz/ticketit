package pl.krzysztofgwizdz.ticketit.service;

import pl.krzysztofgwizdz.ticketit.dto.PasswordDto;
import pl.krzysztofgwizdz.ticketit.dto.UserBasicDto;
import pl.krzysztofgwizdz.ticketit.dto.UserDto;
import pl.krzysztofgwizdz.ticketit.dto.UserListDto;
import pl.krzysztofgwizdz.ticketit.entity.User;
import pl.krzysztofgwizdz.ticketit.error.UserAlreadyExistsException;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    List<User> findAllUsers();

    void updateUserStatuses(UserListDto userList);

    User signUpNewUser(UserDto userDto) throws UserAlreadyExistsException;

    void updateUserNames(UserBasicDto userDto);

    void updatePassword(PasswordDto passwordDto);
}
