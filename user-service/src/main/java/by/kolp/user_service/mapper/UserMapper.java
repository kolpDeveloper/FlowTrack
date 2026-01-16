package by.kolp.user_service.mapper;

import by.kolp.user_service.model.dto.UserCreatingRequestDTO;
import by.kolp.user_service.model.dto.UserRegistrationDTO;
import by.kolp.user_service.model.entity.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toUser(UserCreatingRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
    UserRegistrationDTO toRegistrationDTO(User user);
}
