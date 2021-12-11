package pl.krzysztofgwizdz.ticketit.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TicketDto {

    @NotNull(message = "form.field.error.required")
    @NotEmpty(message = "form.field.error.required")
    @Size(min = 3, max = 100, message = "tickets.form.field.error.title")
    private String title;

    private String content;

    public TicketDto() {
    }

    public TicketDto(String title, String content) {
        this.title = title;
        this.content = content;
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
}
