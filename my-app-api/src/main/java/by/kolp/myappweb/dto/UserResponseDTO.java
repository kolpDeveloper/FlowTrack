package by.kolp.myappweb.dto;

import by.kolp.myappcore.model.enums.Role;
import lombok.Builder;

@Builder
public record UserResponseDTO(Integer id, String username, String email, Role role, String password) {
}
