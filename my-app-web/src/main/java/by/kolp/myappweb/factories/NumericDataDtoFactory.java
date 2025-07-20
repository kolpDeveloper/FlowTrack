package by.kolp.myappweb.factories;

import by.kolp.myappcore.model.dto.NumericDataDTO;
import by.kolp.myappcore.model.dto.NumericDataEntryDTO;
import by.kolp.myappcore.model.entity.NumericData;
import by.kolp.myappcore.model.entity.NumericDataEntry;

import java.util.List;
import java.util.stream.Collectors;

import static by.kolp.myappcore.model.enums.RoleName.ROLE_USER;

public class NumericDataDtoFactory {

    public NumericDataDTO makeNumericDataDto(NumericDataEntry entry) {


        NumericData numericData = entry.getNumericData();

        List<NumericDataEntryDTO> entryDtos = numericData.getEntriesList().stream()
                .map(e -> new NumericDataEntryDTO(e.getKey(), e.getValue(), e.getCreatedAt(), ROLE_USER))
                .collect(Collectors.toList());

        return new NumericDataDTO(numericData.getId(), entryDtos);
    }

}
