package by.kolp.notificationservice.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record EmailSendingResult(@NotNull int successfulEmail, @NotNull int failedEmail, @NotEmpty List<String> failedAddress) {
}
