package by.kolp.myappweb.dto;

import by.kolp.myappcore.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Builder
public record UserCreatingRequestDTO(@NotEmpty(message = "Username cannot be empty")
                                     @Size(min = 2, max = 30)
                                     String username,

                                     @NotEmpty(message = "Email cannot be empty")
                                     @Email(message = "Email should be valid")
                                     String email,

                                     @NotEmpty(message = "Password cannot be empty")
                                     @Size(min = 8, max = 100)
                                     String password,

                                     Role role) {


}
