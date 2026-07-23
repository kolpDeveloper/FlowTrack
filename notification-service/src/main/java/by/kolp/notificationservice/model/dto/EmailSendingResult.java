package by.kolp.notificationservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record EmailSendingResult(
        @NotNull int successfulEmail,
        @NotNull int failedEmail,
        @NotNull List<String> failedAddress, //not empty annotation won't pass validation
        @NotBlank String message
) {
    public EmailSendingResult(String message) {
        this(0, 0, List.of(), message);
    }
}