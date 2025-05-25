package by.kolp.api.factories;

import by.kolp.api.model.dto.NumericDataEntryDTO;
import by.kolp.api.model.entity.NumericDataEntry;

public class NumericDataEntryDtoFactory {

    public NumericDataEntryDTO makeNumericDataEntryDto(NumericDataEntry entry) {
        return new NumericDataEntryDTO(
                entry.getKey(),
                entry.getValue(),
                entry.getCreatedAt()
        );
    }
}
