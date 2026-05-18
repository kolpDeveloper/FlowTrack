package by.kolp.notificationservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;


public record AdminEmailRequest(@NotEmpty Page<String> to, @NotBlank String subject,@NotBlank String body)
{
}
