package pl.krzysztofgwizdz.ticketit.entity.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProjectUserRoleId implements Serializable {

    private static final long serialVersionUID = -5939338969540237599L;

    @Column(name = "projectid")
    private Long projectId;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "projectroleid")
    private Long projectRoleId;

    public ProjectUserRoleId() {
    }

    /**
     * @param projectId
     * @param userId
     * @param projectRoleId
     */
    public ProjectUserRoleId(Long projectId, Long userId, Long projectRoleId) {
        this.projectId = projectId;
        this.userId = userId;
        this.projectRoleId = projectRoleId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectRoleId() {
        return projectRoleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectUserRoleId)) return false;
        ProjectUserRoleId that = (ProjectUserRoleId) o;
        return getProjectId().equals(that.getProjectId()) && getUserId().equals(that.getUserId()) && getProjectRoleId().equals(that.getProjectRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectId(), getUserId(), getProjectRoleId());
    }
}
