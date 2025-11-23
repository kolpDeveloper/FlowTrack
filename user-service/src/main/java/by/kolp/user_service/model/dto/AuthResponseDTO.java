package by.kolp.user_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponseDTO(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("expires_in")
        Long expiresIn,

        @JsonProperty("token_type")
        String tokenType,

        String username,

        String role,

        String refreshToken

) {
        public AuthResponseDTO(String accessToken, Long expiresIn, String username, String role, String refreshToken) {
                this(accessToken, expiresIn, "Bearer", username, role, refreshToken);
        }
}

