package by.kolp.notificationservice.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record EmailSendingResult(int successfulEmail, int failedEmail, List<String> failedAddress) {
}
