package pl.krzysztofgwizdz.ticketit.service;

import org.mockito.Mock;
import pl.krzysztofgwizdz.ticketit.dao.ProjectRepository;
import pl.krzysztofgwizdz.ticketit.dao.ProjectRoleRepository;
import pl.krzysztofgwizdz.ticketit.dao.UserRepository;
import pl.krzysztofgwizdz.ticketit.entity.Project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository mockProjectRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private ProjectRoleRepository mockProjectRoleRepository;

    @Mock
    private Project mockProject;

    public void setUp() {
        when(mockProjectRepository.findByAcronym(any(String.class))).thenReturn(mockProject);
        when
        ProjectService projectService = new ProjectServiceImpl(mockProjectRepository, mockUserRepository, mockProjectRoleRepository);
    }
}
