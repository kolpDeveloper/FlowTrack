package by.kolp.user_service.model.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDTO(

        @NotBlank String refreshToken

) {

}
