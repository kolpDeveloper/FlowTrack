package by.kolp.myappweb.mapper;

import by.kolp.myappcore.model.entity.NumericDataEntry;
import by.kolp.myappweb.dto.NumericDataEntryDTO;
import org.springframework.stereotype.Component;

@Component
public class NumericMapperImpl implements NumericMapper {
    @Override
    public NumericDataEntryDTO map(NumericDataEntry entry) {
        if (entry == null) {
            return null;
        }


        return new NumericDataEntryDTO(
                entry.getKey(),
                entry.getValue(),
                entry.getCreatedAt());
    }
}
