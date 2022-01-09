package pl.krzysztofgwizdz.ticketit.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krzysztofgwizdz.ticketit.entity.Project;
import pl.krzysztofgwizdz.ticketit.entity.ProjectRole;
import pl.krzysztofgwizdz.ticketit.entity.ProjectUserRoleLink;
import pl.krzysztofgwizdz.ticketit.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final EntityManager entityManager;

    @Autowired
    public ProjectRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Project> findAllProjects() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        criteriaQuery.from(Project.class);
        return session.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Project findProject(long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.byId(Project.class).load(id);
    }

    @Override
    public Project findByAcronym(String acronym) {
        Session session = entityManager.unwrap(Session.class);
        return session.bySimpleNaturalId(Project.class).load(acronym);
    }

    @Override
    public Project saveProject(Project project) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(project);
        return project;
    }

    @Override
    public Project updateProject(Project project) {
        Project managed = findProject(project.getProjectId());
        managed.setName(project.getName());
        managed.setDescription(project.getDescription());
        entityManager.merge(managed);
        return managed;
    }

    @Override
    public void deleteProject(Project project) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(project);
    }

    @Override
    public Project addUserWithRole(Project project, User user, ProjectRole projectRole) {
        Session session = entityManager.unwrap(Session.class);
        project.addUserWithRole(user, projectRole);
        session.saveOrUpdate(project);
        session.saveOrUpdate(user);
        session.saveOrUpdate(projectRole);
        return project;
    }

    @Override
    public void removeRoleFromUser(Project project, User user, ProjectRole projectRole) {
        Session session = entityManager.unwrap(Session.class);
        Project dbProject = session.byId(Project.class).load(project.getProjectId());
        User dbUser = session.byId(User.class).load(user.getUserId());
        ProjectRole dbProjectRole = session.byId(ProjectRole.class).load(projectRole.getRoleId());
        dbProject.removeRoleFromUser(dbUser, dbProjectRole);
        session.saveOrUpdate(dbProject);
        session.saveOrUpdate(dbUser);
        session.saveOrUpdate(dbProjectRole);
    }

    @Override
    public Project findProjectWithUsers(Long id) {
        return entityManager.createQuery("select p " +
                "from Project p " +
                "join fetch p.projectUserRoleLink purl " +
                "join fetch purl.user " +
                "join fetch purl.role " +
                "where p.projectId = :projectId", Project.class)
                .setParameter("projectId", id)
                .getSingleResult();
    }
}
