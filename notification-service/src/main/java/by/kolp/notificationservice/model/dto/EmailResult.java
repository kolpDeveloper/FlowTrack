package by.kolp.notificationservice.model.dto;

import java.util.List;

public record EmailResult(int failed, int success, List<String> failedEmails) {
}
