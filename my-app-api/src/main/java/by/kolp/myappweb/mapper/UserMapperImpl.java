package by.kolp.myappweb.mapper;

import by.kolp.myappcore.model.entity.User;
import by.kolp.myappweb.dto.UserCreatingRequestDTO;
import by.kolp.myappweb.dto.UserRegistrationDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User toUser(UserCreatingRequestDTO dto) {
        if(dto == null) return null;
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setEmail(dto.email());

        return user;
    }

    @Override
    public UserRegistrationDTO toRegistrationDTO(User user) {
        if(user == null) return null;
        return new UserRegistrationDTO(user.getUsername(), user.getPassword(), user.getEmail());
    }
}
