package by.kolp.myappweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.Instant;

@Builder
public record   NumericDataEntryDTO(String key, @Size(max = 2_000_000) Integer value, @JsonProperty("created_at") Instant createdAt) {
}
