package pl.krzysztofgwizdz.ticketit.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordDto {

    @NotNull(message = "form.field.error.required")
    @NotEmpty(message = "form.field.error.required")
    @Size(min = 6, max = 40, message = "signup.form.field.error.username.length")
    private String username;

    @NotNull(message = "form.field.error.required")
    @NotEmpty(message = "form.field.error.required")
    @Size(min = 6, max = 40, message = "signup.form.field.error.password.length")
    private String oldPass;

    @NotNull(message = "form.field.error.required")
    @NotEmpty(message = "form.field.error.required")
    @Size(min = 6, max = 40, message = "signup.form.field.error.password.length")
    private String newPass;

    public PasswordDto() {
    }

    public PasswordDto(String username, String oldPass, String newPass) {
        this.username = username;
        this.oldPass = oldPass;
        this.newPass = newPass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
