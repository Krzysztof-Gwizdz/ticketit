package pl.krzysztofgwizdz.ticketit.service;

import pl.krzysztofgwizdz.ticketit.entity.ProjectUserRoleLink;

import java.util.Set;

public interface ProjectService {

    Set<ProjectUserRoleLink> getProjectUserRoleLinksByUser(String username);
}
