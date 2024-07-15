package net.luismarquez.projects.MovieManagement.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SaveUser(
        @Pattern(regexp = "[a-zA-Z0-9-_]{8,255}", message = "{saveUser.username.pattern}") String username,
        @Size(max = 255, message = "{generic.size}") String name,
        @Size(min = 10, max = 255, message = "{generic.size}") @NotBlank(message = "{generic.notblank}") String password,
        @JsonProperty(value = "password_repeated")
        @Size(min = 10, max = 255, message = "{generic.size}") @NotBlank(message = "{generic.notblank}") String passwordRepeated
) implements Serializable {
}
