package pl.krzysztofgwizdz.ticketit.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krzysztofgwizdz.ticketit.entity.ProjectRole;

import javax.persistence.EntityManager;

@Repository
public class ProjectRoleRepositoryImpl implements ProjectRoleRepository {

    private EntityManager entityManager;

    public ProjectRoleRepositoryImpl() {
    }

    @Autowired
    public ProjectRoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public ProjectRole findByRoleName(String rolename) {
        Session session = entityManager.unwrap(Session.class);
        return session.bySimpleNaturalId(ProjectRole.class).load(rolename);
    }

    @Override
    public ProjectRole findById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.byId(ProjectRole.class).load(id);
    }
}
