package pl.krzysztofgwizdz.ticketit.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProjectDto {

    @NotNull(message = "form.field.error.required")
    @NotEmpty(message = "form.field.error.required")
    @Size(min = 6, max = 255, message = "projects.form.field.error.name")
    private String name;

    @NotNull(message = "form.field.error.required")
    @NotEmpty(message = "form.field.error.required")
    @Size(min = 2, max = 50, message = "projects.form.field.error.acronym")
    private String acronym;

    private String description;

    public ProjectDto() {
    }

    public ProjectDto(String name, String acronym, String description) {
        this.name = name;
        this.acronym = acronym;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
