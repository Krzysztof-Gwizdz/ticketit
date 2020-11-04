package pl.krzysztofgwizdz.ticketit.service;

import pl.krzysztofgwizdz.ticketit.entity.User;
import pl.krzysztofgwizdz.ticketit.dto.UserDto;
import pl.krzysztofgwizdz.ticketit.error.UserAlreadyExistsException;

public interface UserService {

    User signUpNewUser(UserDto userDto) throws UserAlreadyExistsException;
}
