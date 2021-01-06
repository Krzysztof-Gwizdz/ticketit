package pl.krzysztofgwizdz.ticketit.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @Column(name = "authorityid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "authority_name", unique = true, nullable = false)
    private String authorityName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_authorities",
            joinColumns = {@JoinColumn(name = "authorityid")},
            inverseJoinColumns = {@JoinColumn(name = "userid")}
    )
    private List<User> userList;

    public Authority() {
        userList = new ArrayList<>();
    }

    /**
     * @param authorityName Name of the authority (i.e. ROLE_USER)
     */
    public Authority(String authorityName) {
        this.authorityName = authorityName;
        this.userList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        return authorityName != null ? authorityName.equals(authority.authorityName) : authority.authorityName == null;
    }

    @Override
    public int hashCode() {
        return authorityName != null ? authorityName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return authorityName;
    }
}
