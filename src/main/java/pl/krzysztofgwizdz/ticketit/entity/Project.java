package pl.krzysztofgwizdz.ticketit.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "projects")
@NaturalIdCache
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "name")
    private String name;

    @NaturalId
    @Column(name = "acronym")
    private String acronym;

    @Column(name = "description")
    private String description;

    @Column(name = "created_on")
    private Date createdOn = new Date();

    @OneToMany(
            mappedBy = "project",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    private Set<ProjectUserRoleLink> projectUserRoleLink = new HashSet<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "project_id")
    private Set<Ticket> tickets = new HashSet<>();

    public Project() {
    }

    /**
     * @param name
     * @param acronym
     * @param description
     */
    public Project(String name, String acronym, String description) {
        this.name = name;
        this.acronym = acronym;
        this.description = description;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedOn() {
        return  createdOn;
    }

    public Set<ProjectUserRoleLink> getProjectUserRoleLink() {
        return projectUserRoleLink;
    }

    public void setProjectUserRoleLink(Set<ProjectUserRoleLink> projectUserRoleLink) {
        this.projectUserRoleLink = projectUserRoleLink;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addUserWithRole(User user, ProjectRole projectRole) {
        ProjectUserRoleLink projectUserRoleLink = new ProjectUserRoleLink(this, user, projectRole);
        this.projectUserRoleLink.add(projectUserRoleLink);
        user.getProjectUserRoleLinks().add(projectUserRoleLink);
        projectRole.getProjectUserRoleLink().add(projectUserRoleLink);
    }

    public void removeRoleFromUser(User user, ProjectRole projectRole) {
        Iterator<ProjectUserRoleLink> iterator = projectUserRoleLink.iterator();
        while (iterator.hasNext()) {
            ProjectUserRoleLink link = iterator.next();
            if (link.getProject().equals(this) && link.getUser().equals(user) && link.getRole().equals(projectRole)) {
                iterator.remove();
                link.getUser().getProjectUserRoleLinks().remove(link);
                link.getRole().getProjectUserRoleLink().remove(link);
                link.setProject(null);
                link.setUser(null);
                link.setRole(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return getName().equals(project.getName()) && getAcronym().equals(project.getAcronym());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAcronym());
    }
}
