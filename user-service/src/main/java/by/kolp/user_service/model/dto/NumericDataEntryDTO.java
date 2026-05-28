package by.kolp.user_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record NumericDataEntryDTO(UUID id, @JsonProperty("user_id") UUID userId, String key, BigDecimal value, @JsonProperty("created_at") Instant createdAt,
                                  CategoryNameDTO category) {
}
