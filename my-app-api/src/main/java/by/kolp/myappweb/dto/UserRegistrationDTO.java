package by.kolp.myappweb.dto;

import lombok.Builder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Builder
public record UserRegistrationDTO(
        @NotEmpty(message = "Username cannot be empty") @Size(min = 2, max = 30) String username
        , @NotEmpty(message = "Email cannot be empty") @Email(message = "Email should be valid") String email
        ,@NotEmpty(message = "Email cannot be empty") @Size(min = 8, max = 52) String password) {
}
