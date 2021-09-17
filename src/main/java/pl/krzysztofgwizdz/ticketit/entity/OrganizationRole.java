package pl.krzysztofgwizdz.ticketit.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "organization_roles")
public class OrganizationRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizationroleid")
    private Long roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("organizationid")
    private Organization organization;

    @Column(name = "rolename")
    private String roleName;

    public OrganizationRole() {
    }

    public OrganizationRole(Long roleId, Organization organization, String roleName) {
        this.roleId = roleId;
        this.organization = organization;
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationRole)) return false;
        OrganizationRole that = (OrganizationRole) o;
        return getRoleId().equals(that.getRoleId()) && getOrganization().equals(that.getOrganization()) && getRoleName().equals(that.getRoleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleId(), getOrganization(), getRoleName());
    }
}
