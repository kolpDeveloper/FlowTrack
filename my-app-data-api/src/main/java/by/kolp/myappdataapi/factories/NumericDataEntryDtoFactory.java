package by.kolp.myappdataapi.factories;

import by.kolp.myappcore.model.entity.NumericDataEntry;
import by.kolp.myappdataapi.dto.NumericDataEntryDTO;


public class NumericDataEntryDtoFactory {
    public NumericDataEntryDTO makeNumericDataEntryDto(NumericDataEntry entry) {
        return new NumericDataEntryDTO(
                entry.getKey(),
                entry.getValue(),
                entry.getCreatedAt()
        );
    }

}