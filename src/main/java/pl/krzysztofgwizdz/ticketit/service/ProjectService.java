package pl.krzysztofgwizdz.ticketit.service;

import pl.krzysztofgwizdz.ticketit.entity.Project;
import pl.krzysztofgwizdz.ticketit.entity.ProjectUserRoleLink;

import java.util.List;
import java.util.Set;

public interface ProjectService {

    Set<ProjectUserRoleLink> getProjectUserRoleLinksByUser(String username);

    List<Project> getProjectsByUsername(String username);
}
