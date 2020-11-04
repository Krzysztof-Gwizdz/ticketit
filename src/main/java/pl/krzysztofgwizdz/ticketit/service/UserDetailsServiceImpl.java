package pl.krzysztofgwizdz.ticketit.service;

import org.springframework.beans.factory.annotation.Value;
import pl.krzysztofgwizdz.ticketit.entity.User;
import pl.krzysztofgwizdz.ticketit.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${password.salt}")
    private String passwordSalt;

    private final UserRepository userDetailsRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userDetailsRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDetailsRepository.findUserByUsername(username);
        UserBuilder builder;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.disabled(!user.isEnabled());
            builder.password(user.getPassword()+"${password.salt}");
            String[] authorities = user.getAuthorities()
                    .stream().map(a -> a.getAuthority()).toArray(String[]::new);
            builder.authorities(authorities);
            return builder.build();
        }
        throw new UsernameNotFoundException("User not found.");
    }
}
