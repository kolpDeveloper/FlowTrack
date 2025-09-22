package by.kolp.myappweb.dto;

import by.kolp.myappcore.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreatingRequestDTO {


    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 2, max = 30)
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 100)
    private String password;

    private Role role = Role.USER;
}
