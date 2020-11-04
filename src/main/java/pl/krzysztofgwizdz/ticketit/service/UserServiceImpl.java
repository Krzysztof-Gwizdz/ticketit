package pl.krzysztofgwizdz.ticketit.service;

import org.springframework.beans.factory.annotation.Value;
import pl.krzysztofgwizdz.ticketit.entity.Authority;
import pl.krzysztofgwizdz.ticketit.entity.User;
import pl.krzysztofgwizdz.ticketit.dto.UserDto;
import pl.krzysztofgwizdz.ticketit.error.UserAlreadyExistsException;
import pl.krzysztofgwizdz.ticketit.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Value("${password.salt}")
    private String passwordSalt;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User signUpNewUser(UserDto userDto) throws UserAlreadyExistsException {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority("ROLE_USER"));
        if (userExists(userDto.getUsername(), userDto.getEmail())) {
            throw new UserAlreadyExistsException("There is already user with that username or email.");
        }
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()+passwordSalt));
        newUser.setEmail(userDto.getEmail());
        newUser.setEnabled(false);
        newUser.setAuthorities(authorities);
        return userRepository.save(newUser);
    }


    private boolean userExists(String username, String email) {
        List<User> users = userRepository.findUserByUsernameOrEmail(username, email);
        return users != null && users.size() > 0;
    }
}
