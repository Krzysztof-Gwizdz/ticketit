package pl.krzysztofgwizdz.ticketit.dto;

import pl.krzysztofgwizdz.ticketit.annotation.PasswordMatches;
import pl.krzysztofgwizdz.ticketit.annotation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches(message = "signup.form.field.error.password.nomatch")
public class UserDto {

    @NotNull(message = "form.field.error.required")
    @NotEmpty(message = "form.field.error.required")
    @Size(min = 6, max = 40, message = "signup.form.field.error.username.length")
    private String username;

    @NotNull(message = "form.field.error.required")
    @NotEmpty(message = "form.field.error.required")
    @Size(min = 6, max = 40, message = "signup.form.field.error.password.length")
    private String password;

    @NotNull(message = "form.field.error.required")
    @NotEmpty(message = "form.field.error.required")
    private String matchingPassword;

    @NotNull(message = "form.field.error.required")
    @NotEmpty(message = "form.field.error.required")
    @ValidEmail(message = "signup.form.field.error.email.format")
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
