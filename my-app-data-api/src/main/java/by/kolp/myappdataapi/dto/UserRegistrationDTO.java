package by.kolp.myappdataapi.dto;

import lombok.Builder;

@Builder
public record UserRegistrationDTO(String username, String email, String password) {
}
