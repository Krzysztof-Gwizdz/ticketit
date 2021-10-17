package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.Project;
import pl.krzysztofgwizdz.ticketit.entity.ProjectRole;
import pl.krzysztofgwizdz.ticketit.entity.User;

import java.util.List;

public interface ProjectRepository {

    List<Project> findAllProjects();

    Project findProject(long id);

    /**
     * @param acronym Acronym of project
     * @return Found project
     */
    Project findByAcronym(String acronym);

    Project saveProject(Project project);

    void deleteProject(Project project);

    Project addUserWithRole(Project project, User user, ProjectRole projectRole);

    void removeRoleFromUser(Project project, User user, ProjectRole projectRole);
}
