package pl.krzysztofgwizdz.ticketit.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krzysztofgwizdz.ticketit.entity.Organization;
import pl.krzysztofgwizdz.ticketit.entity.Project;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final EntityManager entityManager;

    @Autowired
    public ProjectRepositoryImpl(EntityManager entityManager){
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
    public void saveProject(Project project) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(project);
    }

    @Override
    public void deleteProject(Project project) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(project);
    }
}
