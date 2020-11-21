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

    @Column(name = "authority_name")
    private String authorityName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Users_Authorities",
            joinColumns = { @JoinColumn(name = "authorityid") },
            inverseJoinColumns = { @JoinColumn(name = "userid") }
    )
    private Set<User> userSet;

    public Authority(String authorityName) {
        this.authorityName = authorityName;
        this.userSet = new HashSet<>();
    }
}
