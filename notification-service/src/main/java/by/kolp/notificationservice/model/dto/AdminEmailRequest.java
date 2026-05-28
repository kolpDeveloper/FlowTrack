package by.kolp.notificationservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;


public record AdminEmailRequest(Page<String> to, @NotBlank String subject, @NotBlank String body)
{
}
