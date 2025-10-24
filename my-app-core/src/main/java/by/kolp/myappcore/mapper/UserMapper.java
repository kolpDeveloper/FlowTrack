package by.kolp.myappcore.mapper;

import by.kolp.myappcore.model.dto.UserCreatingRequestDTO;
import by.kolp.myappcore.model.dto.UserRegistrationDTO;
import by.kolp.myappcore.model.entity.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    User toUser(UserCreatingRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
    UserRegistrationDTO toRegistrationDTO(User user);
}
