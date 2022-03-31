package pl.krzysztofgwizdz.ticketit.service;

import pl.krzysztofgwizdz.ticketit.dto.ProjectDto;
import pl.krzysztofgwizdz.ticketit.entity.Project;
import pl.krzysztofgwizdz.ticketit.entity.ProjectUserRoleLink;

import java.util.Set;

public interface ProjectService {

    Set<ProjectUserRoleLink> getProjectUserRoleLinksByUser(String username);

    Project saveProjectWithUserAndRole(String username, ProjectDto projectDto);

    Project updateProject(Project project);

    Project getProjectByAcronym(String projectAcronym);

    Set<ProjectUserRoleLink> getProjectUserRoleLinksByProject(Long projectId);
}
