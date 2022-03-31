package pl.krzysztofgwizdz.ticketit.entity.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProjectUserRoleId implements Serializable {

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    public ProjectUserRoleId() {
    }

    /**
     * @param projectId
     * @param userId
     * @param roleId
     */
    public ProjectUserRoleId(Long projectId, Long userId, Long roleId) {
        this.projectId = projectId;
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectUserRoleId)) return false;
        ProjectUserRoleId that = (ProjectUserRoleId) o;
        return getProjectId().equals(that.getProjectId()) && getUserId().equals(that.getUserId()) && getRoleId().equals(that.getRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectId(), getUserId(), getRoleId());
    }
}
