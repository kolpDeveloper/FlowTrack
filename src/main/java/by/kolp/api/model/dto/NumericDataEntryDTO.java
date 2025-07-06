package by.kolp.api.model.dto;

import by.kolp.api.model.entity.Role;
import by.kolp.api.model.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Builder
public record   NumericDataEntryDTO(String key, Integer value, @JsonProperty("created_at") Instant createdAt, RoleName roleName) {
}
