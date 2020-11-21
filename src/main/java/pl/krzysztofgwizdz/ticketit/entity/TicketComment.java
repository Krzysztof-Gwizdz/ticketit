package pl.krzysztofgwizdz.ticketit.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ticket_comments")
public class TicketComment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content")
    private String content;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "ticketid")
    private Ticket ticket;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinColumn(name = "userid")
    private User user;

    public TicketComment(String content, Ticket ticket, User user) {
        this.content = content;
        this.ticket = ticket;
        this.user = user;
    }
}
