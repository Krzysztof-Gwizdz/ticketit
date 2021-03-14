package pl.krzysztofgwizdz.ticketit.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projectid")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "users_projects",
            joinColumns = {@JoinColumn(name = "projectid")},
            inverseJoinColumns = {@JoinColumn(name = "userid")}
    )
    private List<User> members;

    @ManyToOne
    @JoinColumn(name = "organiztionid")
    private Organization organization;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "projectid")
    List<Ticket> tickets;
}
