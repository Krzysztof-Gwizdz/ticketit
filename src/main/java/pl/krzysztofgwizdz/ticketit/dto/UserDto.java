package pl.krzysztofgwizdz.ticketit.dto;

import pl.krzysztofgwizdz.ticketit.annotation.PasswordMatches;
import pl.krzysztofgwizdz.ticketit.annotation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches(message = "* Hasła muszą być takie same.")
public class UserDto {

    @NotNull(message = "* Pole jest wymagane.")
    @NotEmpty(message = "* Pole jest wymagane.")
    @Size(min=6, max = 40, message = "* Nazwa użytkownika musi mieć od 6 do 40 znaków.")
    private String username;

    @NotNull(message = "* Pole jest wymagane.")
    @NotEmpty(message = "* Pole jest wymagane.")
    @Size(min=6, max = 40, message = "* Hasło musi mieć od 6 do 40 znaków.")
    private String password;

    @NotNull(message = "* Pole jest wymagane.")
    @NotEmpty(message = "* Pole jest wymagane.")
    private String matchingPassword;

    @NotNull(message = "* Pole jest wymagane.")
    @NotEmpty(message = "* Pole jest wymagane.")
    @ValidEmail(message = "* Niepoprawny format adresu email.")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
