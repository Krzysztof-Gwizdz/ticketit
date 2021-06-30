package pl.krzysztofgwizdz.ticketit.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrganizationDTO {

    @NotNull(message = "* Pole jest wymagane.")
    @NotEmpty(message = "* Pole jest wymagane.")
    @Size(min = 6, max = 255, message = "* Nazwa użytkownika musi mieć od 6 do 255 znaków.")
    private String fullName;

    @NotNull(message = "* Pole jest wymagane.")
    @NotEmpty(message = "* Pole jest wymagane.")
    @Size(min = 3, max = 50, message = "* Nazwa użytkownika musi mieć od 3 do 50 znaków.")
    private String shortName;

    public OrganizationDTO() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
