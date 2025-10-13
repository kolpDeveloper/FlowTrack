package by.kolp.myappcore.mapper;

import by.kolp.myappcore.model.dto.NumericDataEntryDTO;
import by.kolp.myappcore.model.entity.NumericDataEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NumericMapper {

    NumericDataEntryDTO map(NumericDataEntry entry);

    NumericDataEntry toEntity(NumericDataEntryDTO dto);

    NumericDataEntryDTO toDto(NumericDataEntry entry);
}
