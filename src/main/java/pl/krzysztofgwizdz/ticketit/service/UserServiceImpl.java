package pl.krzysztofgwizdz.ticketit.service;

import org.springframework.beans.factory.annotation.Value;
import pl.krzysztofgwizdz.ticketit.dao.AuthorityRepository;
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

    @Value("${users.default.authority}")
    private String defaultAuthority;

    private UserRepository userRepository;

    private AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    @Transactional
    public User signUpNewUser(UserDto userDto) throws UserAlreadyExistsException {
        Set<Authority> userAuthorities = new HashSet<>();
        Set<Authority> authoritySet = authorityRepository.getAuthorities();
        if (userExists(userDto.getUsername(), userDto.getEmail())) {
            throw new UserAlreadyExistsException("There is already user with that username or email.");
        }
        String encodedPassword = passwordEncoder.encode(userDto.getPassword()+passwordSalt);
        User newUser = new User(userDto.getUsername(), encodedPassword, userDto.getEmail());
        newUser.setEnabled(false);
        if(authoritySet.contains(new Authority(defaultAuthority))){
            userAuthorities.add(new Authority(defaultAuthority));
        }
        newUser.setAuthorities(userAuthorities);
        return userRepository.save(newUser);
    }


    private boolean userExists(String username, String email) {
        List<User> users = userRepository.findUserByUsernameOrEmail(username, email);
        return users != null && users.size() > 0;
    }
}
