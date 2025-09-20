package by.kolp.myappweb.dto;

import by.kolp.myappcore.model.entity.Role;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserResponseDTO(Integer id, String username, String email, Role role, String password) {
}
