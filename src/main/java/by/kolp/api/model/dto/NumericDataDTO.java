package by.kolp.api.model.dto;

import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public record NumericDataDTO(Long id, @Singular List<NumericDataEntryDTO> entries) {
}

