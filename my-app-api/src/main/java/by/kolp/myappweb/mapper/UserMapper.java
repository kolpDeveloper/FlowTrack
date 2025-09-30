package by.kolp.myappweb.mapper;

import by.kolp.myappcore.model.entity.User;
import by.kolp.myappweb.dto.UserCreatingRequestDTO;
import by.kolp.myappweb.dto.UserRegistrationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreatingRequestDTO dto);

    UserRegistrationDTO toRegistrationDTO(User user);

}
