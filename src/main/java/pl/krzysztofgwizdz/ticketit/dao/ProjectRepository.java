package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.Project;

import java.util.List;

public interface ProjectRepository {

    List<Project> findAllProjects();

    Project findProject(long id);

    void saveProject(Project project);

    void deleteProject(Project project);
}
