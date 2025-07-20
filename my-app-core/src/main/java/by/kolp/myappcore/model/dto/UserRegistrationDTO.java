package by.kolp.myappcore.model.dto;

import lombok.Builder;

@Builder
public record UserRegistrationDTO(String username, String email, String password) {
}
