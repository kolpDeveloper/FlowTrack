package by.kolp.notificationservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record SubjectMessageDTO(@NotBlank String subject,@NotBlank String message) {
}
