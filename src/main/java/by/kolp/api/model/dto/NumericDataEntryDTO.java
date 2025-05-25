package by.kolp.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record NumericDataEntryDTO(String key, Integer value, @JsonProperty("created_at") Instant createdAt) {
}
