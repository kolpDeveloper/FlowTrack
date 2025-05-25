package by.kolp.api.controller;

import by.kolp.api.factories.UserRegistrationDtoFactory;
import by.kolp.api.model.dto.AckDTO;
import by.kolp.api.model.dto.UserCreatingRequestDTO;
import by.kolp.api.model.dto.UserRegistrationDTO;
import by.kolp.api.model.entity.User;
import by.kolp.api.model.exceptions.BadRequestException;
import by.kolp.api.model.exceptions.NotFoundException;
import by.kolp.api.repository.interfaces.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
public class UserController {


    final UserRepository userRepository;


    final UserRegistrationDtoFactory userRegistrationDtoFactory;

    public static final String CREATE_USER = "/api/user";
    public static final String EDIT_USER = "/api/user/{user_id}";
    public static final String FETCH_USERS = "/api/user";
    public static final String DELETE_USER = "/api/user/{user_id}";


    @DeleteMapping(value = DELETE_USER)
    public AckDTO deleteUser(@PathVariable("user_id") Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(String
                                .format("User \"%s%\" doesn't exist", userId)));


        userRepository.deleteUserBy(user.getId());
        return new AckDTO("User successfully deleted", true);
    }

    @GetMapping(FETCH_USERS)
    public List<UserRegistrationDTO> fetchUsers(@RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());


        Stream<User> users = optionalPrefixName.stream()
                .map(userRepository::streamAllByUsernameStartingWithIgnoreCase)
                .findAny().orElseGet(userRepository::streamAll);


        return users
                .map(userRegistrationDtoFactory::makeUserRegistrationDto)
                .collect(Collectors.toList());
    }


    @PostMapping(CREATE_USER)
    public UserRegistrationDTO register(@RequestBody UserCreatingRequestDTO request) {

        if (request.getUsername().trim().isEmpty() || request.getPassword().trim().isEmpty()) {
            throw new BadRequestException("Username or password cannot be empty");
        }

        userRepository
                .findByUsername(request.getUsername())
                .ifPresent(
                        user -> {
                            throw new BadRequestException(String.format("User \"%s\" already exists.", request.getUsername()));
                        });

        User newUser = userRepository.saveAndFlush(
                User.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .build()
        );

        return userRegistrationDtoFactory.makeUserRegistrationDto(newUser);
    }

    @PatchMapping(EDIT_USER)
    public UserRegistrationDTO editUsername(@PathVariable("user_id") Long userId,
                                            @RequestBody UserCreatingRequestDTO request) {


        if (request.getUsername().trim().isEmpty()) {
            throw new BadRequestException("Username cannot be empty");
        }

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(String
                                .format("User \"%s%\" doesn't exist", userId)));

        userRepository.findByUsername(user.getUsername())
                .filter(anotherUser -> !Objects.equals(anotherUser.getId(), userId))
                .ifPresent(anotherUser -> {
                    throw new BadRequestException(String.format("User \"%s\" already exists.", request.getUsername()));


                });


        user.setUsername(request.getUsername());
        user = userRepository.saveAndFlush(user);
        return userRegistrationDtoFactory.makeUserRegistrationDto(user);
    }
}