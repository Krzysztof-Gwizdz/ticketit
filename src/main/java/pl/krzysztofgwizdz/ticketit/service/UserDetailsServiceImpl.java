package pl.krzysztofgwizdz.ticketit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krzysztofgwizdz.ticketit.dao.AuthorityRepository;
import pl.krzysztofgwizdz.ticketit.dao.UserRepository;
import pl.krzysztofgwizdz.ticketit.entity.Authority;
import pl.krzysztofgwizdz.ticketit.entity.User;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userDetailsRepository;

    private AuthorityRepository authorityRepository;

    public UserDetailsServiceImpl() {
    }

    @Autowired
    public void setUserDetailsRepository(UserRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDetailsRepository.findUserByUsername(username);
        UserBuilder builder;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.disabled(!user.isEnabled());
            builder.password(user.getPassword());
            String[] authorities = user.getAuthorities()
                    .stream().map(Authority::getAuthorityName).toArray(String[]::new);
            builder.authorities(authorities);
            return builder.build();
        }
        throw new UsernameNotFoundException("User not found.");
    }
}
