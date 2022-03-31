package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.ProjectRole;

public interface ProjectRoleRepository {

    ProjectRole findByRoleName(String rolename);

    ProjectRole findById(Long id);

}
