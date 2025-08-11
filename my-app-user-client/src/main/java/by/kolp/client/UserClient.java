package by.kolp.client;

import by.kolp.myappdataapi.dto.UserRegistrationDTO;

import java.util.Optional;

public interface UserClient {
    Optional<UserRegistrationDTO> getUserById(Long id);

    Optional<UserRegistrationDTO> findUserByUsername(String username);
}
