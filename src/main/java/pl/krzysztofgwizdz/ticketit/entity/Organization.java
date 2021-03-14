package pl.krzysztofgwizdz.ticketit.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organiztions")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizationid")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "full_name")
    private String fullName;

    @ManyToMany
    @JoinTable(
            name = "users_organizations",
            joinColumns = {@JoinColumn(name = "organiztionid")},
            inverseJoinColumns = {@JoinColumn(name = "userid")}
    )
    private List<User> members;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(name = "organizationid")
    private List<Project> projects;
}
