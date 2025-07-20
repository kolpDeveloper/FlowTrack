package by.kolp.myappcore.model.dto;

import by.kolp.myappcore.model.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record   NumericDataEntryDTO(String key, Integer value, @JsonProperty("created_at") Instant createdAt, RoleName roleName) {
}
