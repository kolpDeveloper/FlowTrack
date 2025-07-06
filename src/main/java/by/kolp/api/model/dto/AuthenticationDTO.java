package by.kolp.api.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record AuthenticationDTO(@NotEmpty(message = "Username cannot be empty") @Size(min = 3,max = 50) String username,@NotEmpty(message = "Password cannot be empty") @Size(min = 8) String password) {}
