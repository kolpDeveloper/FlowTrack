package by.kolp.myappdataapi.factories;

import by.kolp.myappdataapi.dto.UserResponseDTO;
import by.kolp.myappcore.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponseDtoFactory {

    public UserResponseDTO makeUserResponseDto(User user) {
        return UserResponseDTO
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(null)
                .build();
    }

    public List<UserResponseDTO> makeUserResponseDtoList(List<User> users) {
        return users.stream()
                .map(this::makeUserResponseDto)
                .collect(Collectors.toList());
    }

}
