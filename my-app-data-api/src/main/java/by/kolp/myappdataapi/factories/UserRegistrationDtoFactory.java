package by.kolp.myappdataapi.factories;

import by.kolp.myappdataapi.dto.UserRegistrationDTO;
import by.kolp.myappcore.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationDtoFactory {
    public UserRegistrationDTO makeUserRegistrationDto(User user ) {
        return UserRegistrationDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
