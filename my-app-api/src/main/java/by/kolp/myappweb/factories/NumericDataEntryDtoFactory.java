package by.kolp.myappweb.factories;

import by.kolp.myappcore.model.entity.NumericDataEntry;
import by.kolp.myappweb.dto.NumericDataEntryDTO;
import org.springframework.stereotype.Component;

@Component
public class NumericDataEntryDtoFactory {
    public NumericDataEntryDTO makeNumericDataEntryDto(NumericDataEntry entry) {
        return new NumericDataEntryDTO(
                entry.getKey(),
                entry.getValue(),
                entry.getCreatedAt()
        );
    }

}