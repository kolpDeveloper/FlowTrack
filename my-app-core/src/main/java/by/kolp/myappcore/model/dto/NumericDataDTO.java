package by.kolp.myappcore.model.dto;

import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public record NumericDataDTO(Long id, @Singular List<by.kolp.myappcore.model.dto.NumericDataEntryDTO> entries) {
}

