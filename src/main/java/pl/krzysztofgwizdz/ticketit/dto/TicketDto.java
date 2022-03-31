package pl.krzysztofgwizdz.ticketit.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TicketDto {

    private long ticketId;

    @NotNull(message = "form.field.error.required")
    @NotEmpty(message = "form.field.error.required")
    @Size(min = 3, max = 100, message = "tickets.form.field.error.title")
    private String title;

    private String content;

    private int ticketStatusId;

    public TicketDto() {
    }

    public TicketDto(long ticketId, String title, String content, int ticketStatusId) {
        this.ticketId = ticketId;
        this.title = title;
        this.content = content;
        this.ticketStatusId = ticketStatusId;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
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

    public int getTicketStatusId() {
        return ticketStatusId;
    }

    public void setTicketStatusId(int ticketStatusId) {
        this.ticketStatusId = ticketStatusId;
    }
}
