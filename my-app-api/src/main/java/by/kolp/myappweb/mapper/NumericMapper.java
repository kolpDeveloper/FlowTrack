package by.kolp.myappweb.mapper;

import by.kolp.myappcore.model.entity.NumericDataEntry;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappweb.dto.NumericDataEntryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NumericMapper {

    @Mapping(target = "createdAt", ignore = true)
    NumericDataEntryDTO map(NumericDataEntry entry);

}
