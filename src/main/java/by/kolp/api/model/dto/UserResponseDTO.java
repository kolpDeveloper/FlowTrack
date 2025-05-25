package by.kolp.api.model.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record UserResponseDTO(Long id, String username, String email, Instant updatedAt, Instant lastLoginAt, Instant createdAt) {
}
