package pl.krzysztofgwizdz.ticketit.entity;

import javax.persistence.*;

@Entity
@Table(name = "ticket_statuses")
public class TicketStatus {

    @Id
    @Column(name = "statusid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status_name")
    private String statusName;
}
