package pl.krzysztofgwizdz.ticketit.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statusid")
    private TicketStatus status;

    @OneToMany(fetch = FetchType.LAZY
            , cascade = {CascadeType.ALL})
    @JoinColumn(name = "ticket_id")
    private List<TicketComment> commentList;

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Timestamp modificationDate) {
        this.modificationDate = modificationDate;
    }

    public List<TicketComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<TicketComment> commentList) {
        this.commentList = commentList;
    }

    public void addComment(TicketComment comment) {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
        commentList.add(comment);
    }
}
