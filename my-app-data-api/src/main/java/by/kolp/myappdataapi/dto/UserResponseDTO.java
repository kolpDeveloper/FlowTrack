package by.kolp.myappdataapi.dto;

import by.kolp.myappcore.model.entity.Role;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserResponseDTO(Long id, String username, String email, Role role) {
}
