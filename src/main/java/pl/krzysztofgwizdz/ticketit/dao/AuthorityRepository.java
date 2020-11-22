package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.Authority;

import java.util.Set;

public interface AuthorityRepository {

    Set<Authority> getAuthorities();

    Authority getAuthorityById(int id);

    Authority getAuthorityByName(String name);

    void saveAuthority(String authorityName);
}
