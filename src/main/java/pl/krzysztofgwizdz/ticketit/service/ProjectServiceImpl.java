package pl.krzysztofgwizdz.ticketit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzysztofgwizdz.ticketit.dao.ProjectRepository;
import pl.krzysztofgwizdz.ticketit.dao.UserRepository;
import pl.krzysztofgwizdz.ticketit.entity.Project;
import pl.krzysztofgwizdz.ticketit.entity.ProjectUserRoleLink;
import pl.krzysztofgwizdz.ticketit.entity.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    public ProjectServiceImpl() {
    }

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Set<ProjectUserRoleLink> getProjectUserRoleLinksByUser(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getProjectUserRoleLinks();
    }

    @Override
    @Transactional
    public List<Project> getProjectsByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        Set<ProjectUserRoleLink> projectUserRoleLinkSet = user.getProjectUserRoleLinks();
        if(projectUserRoleLinkSet == null) {
            return null;
        }
        return projectUserRoleLinkSet.stream().map(ProjectUserRoleLink::getProject).collect(Collectors.toList());
//        List<Project> projects = new ArrayList<>();
//        for (ProjectUserRoleLink link : projectUserRoleLinkSet) {
//            projects.add(link.getProject());
//        }
//        return projects;
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
