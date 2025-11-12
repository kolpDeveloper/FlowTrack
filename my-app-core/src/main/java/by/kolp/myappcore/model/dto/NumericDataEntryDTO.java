package by.kolp.myappcore.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record NumericDataEntryDTO(String key, Integer value, @JsonProperty("created_at") Instant createdAt, CategoryNameDTO category) {
}
