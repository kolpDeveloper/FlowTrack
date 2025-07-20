package by.kolp.myappweb.factories;

import by.kolp.myappcore.model.dto.NumericDataEntryDTO;
import by.kolp.myappcore.model.entity.NumericDataEntry;
import by.kolp.myappcore.model.enums.RoleName;


public class NumericDataEntryDtoFactory {

    public NumericDataEntryDTO makeNumericDataEntryDto(NumericDataEntry entry) {
        return new NumericDataEntryDTO(
                entry.getKey(),
                entry.getValue(),
                entry.getCreatedAt(),
                RoleName.ROLE_USER
        );
    }
}
