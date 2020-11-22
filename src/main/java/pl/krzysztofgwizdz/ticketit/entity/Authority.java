package pl.krzysztofgwizdz.ticketit.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @Column(name = "authorityid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "authority_name", unique = true)
    private String authorityName;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "Users_Authorities",
            joinColumns = {@JoinColumn(name = "authorityid")},
            inverseJoinColumns = {@JoinColumn(name = "userid")}
    )
    private Set<User> userSet;

    public Authority() {
    }

    /**
     * @param authorityName
     */
    public Authority(String authorityName) {
        this.authorityName = authorityName;
        this.userSet = new HashSet<>();
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
