package net.luismarquez.projects.MovieManagement.service.validator;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import net.luismarquez.projects.MovieManagement.exception.InvalidPasswordException;

public class PasswordValidator {

    public static void validatePassword(String password, String passwordRepeated){

        if(!StringUtils.hasText(password) || !StringUtils.hasText(passwordRepeated)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST.value(),
                    "Passwords must contain data",
                    new IllegalArgumentException("Passwords must contain data"));
        }

        if(!password.equals(passwordRepeated)){
            throw new InvalidPasswordException(password, passwordRepeated, "Passwords do not match");
        }

        if(!containsNumber(password)){
            throw new InvalidPasswordException(password, "Password must contain at least one number");
        }

        if(!containsUpperCase(password)){
            throw new InvalidPasswordException(password, "Password must contain at least one uppercase letter");
        }

        if(!containsLowerCase(password)){
            throw new InvalidPasswordException(password, "Password must contain at least one lowercase letter");
        }

        if(!containsSpecialCharacter(password)){
            throw new InvalidPasswordException(password, "Password must contain at least one special character");
        }

    }

    private static boolean containsSpecialCharacter(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*");
    }

    private static boolean containsLowerCase(String password) {
        return password.matches(".*[a-z].*");
    }

    private static boolean containsUpperCase(String password) {
        return password.matches(".*[A-Z].*");
    }

    private static boolean containsNumber(String password) {
        return password.matches(".*\\d.*");
    }

}
