package pl.krzysztofgwizdz.ticketit.entity;

import javax.persistence.*;

@Entity
@Table(name = "TicketStatuses")
public class TicketStatus {

    @Id
    @Column(name = "statusid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status_name")
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private String statusName;
}
