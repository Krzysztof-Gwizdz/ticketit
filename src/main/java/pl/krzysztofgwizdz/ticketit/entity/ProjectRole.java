package pl.krzysztofgwizdz.ticketit.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "ProjectRole")
@Table(name = "project_roles")
@NaturalIdCache
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class ProjectRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @NaturalId
    @Column(name = "role_name")
    private String roleName;

    @OneToMany(
            mappedBy = "role",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    private Set<ProjectUserRoleLink> projectUserRoleLink = new HashSet<>();

    public ProjectRole() {
    }

    public ProjectRole(String roleName) {
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<ProjectUserRoleLink> getProjectUserRoleLink() {
        return projectUserRoleLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectRole)) return false;
        ProjectRole that = (ProjectRole) o;
        return getRoleName().equals(that.getRoleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleName());
    }
}
