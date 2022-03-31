package pl.krzysztofgwizdz.ticketit.entity;

import javax.persistence.*;

@Entity
@Table(name = "ticket_statuses")
public class TicketStatus {

    @Id
    @Column(name = "status_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status_name", nullable = false)
    private String statusName;

    public TicketStatus() {
    }

    public TicketStatus(Integer id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "TicketStatus{" +
                "id=" + id +
                ", statusName='" + statusName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketStatus that = (TicketStatus) o;

        return statusName.equals(that.statusName);
    }

    @Override
    public int hashCode() {
        return statusName.hashCode();
    }
}
