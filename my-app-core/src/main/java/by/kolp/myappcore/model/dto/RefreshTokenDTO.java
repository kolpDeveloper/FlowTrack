package by.kolp.myappcore.model.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDTO(

        @NotBlank String refreshToken

) {

}
