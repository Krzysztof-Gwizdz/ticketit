package pl.krzysztofgwizdz.ticketit.validator;

import pl.krzysztofgwizdz.ticketit.annotation.PasswordMatches;
import pl.krzysztofgwizdz.ticketit.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        UserDto userDto = (UserDto) o;
        return userDto.getPassword().equals(userDto.getMatchingPassword());
    }
}
