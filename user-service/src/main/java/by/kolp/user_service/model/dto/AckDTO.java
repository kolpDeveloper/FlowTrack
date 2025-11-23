package by.kolp.user_service.model.dto;


import lombok.Builder;


@Builder
public record AckDTO(String message, Boolean answer) {
}
