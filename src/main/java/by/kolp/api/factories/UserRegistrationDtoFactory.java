package by.kolp.api.factories;

import by.kolp.api.model.dto.UserRegistrationDTO;
import by.kolp.api.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationDtoFactory {
    public UserRegistrationDTO makeUserRegistrationDto(User user) {
        return UserRegistrationDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public UserRegistrationDTO makeUserRegistrationDto(String username) {
        return UserRegistrationDTO.builder()
                .username(username)
                        .build();
    }
}
