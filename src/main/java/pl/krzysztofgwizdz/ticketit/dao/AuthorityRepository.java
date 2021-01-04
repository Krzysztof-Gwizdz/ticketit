package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.Authority;

import java.util.List;

public interface AuthorityRepository {

    List<Authority> getAuthorities();

    Authority getAuthorityById(int id);

    Authority getAuthorityByName(String name);

    void saveAuthority(String authorityName);
}
