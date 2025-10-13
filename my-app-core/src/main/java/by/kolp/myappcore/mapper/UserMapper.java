package by.kolp.myappcore.mapper;

import by.kolp.myappcore.model.dto.UserCreatingRequestDTO;
import by.kolp.myappcore.model.dto.UserRegistrationDTO;
import by.kolp.myappcore.model.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    User toUser(UserCreatingRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    UserRegistrationDTO toRegistrationDTO(User user);
}
