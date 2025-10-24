package by.kolp.myappcore.mapper;

import by.kolp.myappcore.model.dto.NumericDataEntryDTO;
import by.kolp.myappcore.model.entity.NumericDataEntry;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NumericMapper {

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
    NumericDataEntry toEntity(NumericDataEntryDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    NumericDataEntryDTO toDto(NumericDataEntry entry);
}
