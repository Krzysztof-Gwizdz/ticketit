package pl.krzysztofgwizdz.ticketit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzysztofgwizdz.ticketit.dao.ProjectRepository;
import pl.krzysztofgwizdz.ticketit.dao.ProjectRoleRepository;
import pl.krzysztofgwizdz.ticketit.dao.UserRepository;
import pl.krzysztofgwizdz.ticketit.dto.ProjectDto;
import pl.krzysztofgwizdz.ticketit.entity.Project;
import pl.krzysztofgwizdz.ticketit.entity.ProjectRole;
import pl.krzysztofgwizdz.ticketit.entity.ProjectUserRoleLink;
import pl.krzysztofgwizdz.ticketit.entity.User;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private ProjectRoleRepository projectRoleRepository;

    public ProjectServiceImpl() {
    }

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, ProjectRoleRepository projectRoleRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectRoleRepository = projectRoleRepository;
    }

    @Override
    @Transactional
    public Set<ProjectUserRoleLink> getProjectUserRoleLinksByUser(String username) {
        User user = userRepository.findUserByUsernameWithProjects(username);
        Set<ProjectUserRoleLink> roleLinks = user.getProjectUserRoleLinks();
        return roleLinks;
    }

    @Override
    @Transactional
    public Project saveProjectWithUserAndRole(String username, ProjectDto projectDto) {
        User user = userRepository.findUserByUsername(username);
        ProjectRole projectRole = projectRoleRepository.findById(1L);
        Project project = new Project(
                projectDto.getName(),
                projectDto.getAcronym(),
                projectDto.getDescription()
        );
        Project saved = projectRepository.addUserWithRole(project, user, projectRole);
        return saved;
    }

    @Override
    public Project getProjectByAcronym(String projectAcronym) {
        return projectRepository.findByAcronym(projectAcronym);
    }

    @Override
    public Set<ProjectUserRoleLink> getProjectUserRoleLinksByProject(Long projectId) {
        Project project = projectRepository.findProject(projectId);
        Set<ProjectUserRoleLink> links = project.getProjectUserRoleLink();
        return links;
    }

    @Autowired
    public void setProjectRoleRepository(ProjectRoleRepository projectRoleRepository) {
        this.projectRoleRepository = projectRoleRepository;
    }

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
