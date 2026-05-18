package by.kolp.notificationservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;


public record BulkEmailRequest(
        @NotEmpty(message = "Recipients list cannot be empty")
        List<String> recipients,

        @NotBlank(message = "Subject cannot be empty")
        String subject,

        @NotBlank(message = "Body cannot be empty")
        String body)
{
}
