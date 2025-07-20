package by.kolp.myappweb.factories;

import by.kolp.myappcore.model.dto.UserResponseDTO;
import by.kolp.myappcore.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponseDtoFactory {

    private final CategoryDtoFactory categoryDtoFactory;

    public UserResponseDtoFactory(CategoryDtoFactory categoryDtoFactory) {
        this.categoryDtoFactory = categoryDtoFactory;
    } //todo categories

    public UserResponseDTO makeUserResponseDto(User user) {
        return UserResponseDTO
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .updatedAt(user.getUpdatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public List<UserResponseDTO> makeUserResponseDtoList(List<User> users) {
        return users.stream()
                .map(this::makeUserResponseDto)
                .collect(Collectors.toList());
    }

}
