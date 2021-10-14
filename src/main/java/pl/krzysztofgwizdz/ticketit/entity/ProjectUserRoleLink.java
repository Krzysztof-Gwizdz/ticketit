package pl.krzysztofgwizdz.ticketit.entity;

import pl.krzysztofgwizdz.ticketit.entity.embeddable.ProjectUserRoleId;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "project_users")
public class ProjectUserRoleLink {

    @EmbeddedId
    private ProjectUserRoleId projectUserRoleId;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private ProjectRole role;

    @Column(name = "joined_on")
    private Date joinedOn = new Date();

    public ProjectUserRoleLink() {
    }

    public ProjectUserRoleLink(Project project, User user, ProjectRole role) {
        this.project = project;
        this.user = user;
        this.role = role;
        this.projectUserRoleId = new ProjectUserRoleId(project.getProjectId(), user.getUserId(), role.getRoleId());
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProjectRole getRole() {
        return role;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
    }

    public ProjectUserRoleId getProjectUserRoleId() {
        return projectUserRoleId;
    }

    public void setProjectUserRoleId(ProjectUserRoleId projectUserRoleId) {
        this.projectUserRoleId = projectUserRoleId;
    }

    public Date getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(Date joinedOn) {
        this.joinedOn = joinedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectUserRoleLink)) return false;
        ProjectUserRoleLink that = (ProjectUserRoleLink) o;
        return getProject().equals(that.getProject()) && getUser().equals(that.getUser()) && getRole().equals(that.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProject(), getUser(), getRole());
    }
}
