package by.kolp.notificationservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;


public record BulkEmailRequest(

        @NotNull
        Page<String> recipients,

        @NotBlank(message = "Subject cannot be empty")
        String subject,

        @NotBlank(message = "Body cannot be empty")
        String body)
{
}
