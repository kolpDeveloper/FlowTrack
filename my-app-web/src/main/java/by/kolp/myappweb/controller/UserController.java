package by.kolp.myappweb.controller;

import by.kolp.myappdataapi.dto.AckDTO;
import by.kolp.myappdataapi.dto.UserCreatingRequestDTO;
import by.kolp.myappdataapi.dto.UserRegistrationDTO;
import by.kolp.myappcore.model.entity.Role;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.model.enums.RoleName;
import by.kolp.myappcore.repository.interfaces.RoleRepository;
import by.kolp.myappcore.repository.interfaces.UserRepository;
import by.kolp.myappdataapi.exceptions.BadRequestException;
import by.kolp.myappdataapi.exceptions.NotFoundException;
import by.kolp.myappdataapi.factories.UserRegistrationDtoFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api")
@RestController
public class UserController {


    UserRepository userRepository;
    UserRegistrationDtoFactory userRegistrationDtoFactory;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;


    public static final String CREATE_USER = "/user";
    public static final String EDIT_USER = "/user/{user_id}";
    public static final String FETCH_USERS = "/user";
    public static final String DELETE_USER = "/user/{user_id}";


    @DeleteMapping(value = DELETE_USER)
    public AckDTO deleteUser(@PathVariable("user_id") Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(format("User \"%s\" doesn't exist", userId)));


        userRepository.deleteById(user.getId());
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
                            throw new BadRequestException(format("User \"%s\" already exists.", request.getUsername()));
                        });

        Role defaultRole = getOrCreateDefaultRole();

        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(defaultRole)
                .build();

        newUser = userRepository.save(newUser);

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
                        new NotFoundException(format("User \"%s\" doesn't exist", userId)));

        userRepository.findByUsername(request.getUsername())
                .filter(anotherUser -> !Objects.equals(anotherUser.getId(), userId))
                .ifPresent(anotherUser -> {
                    throw new BadRequestException(format("User \"%s\" already exists.", request.getUsername()));
                });

        user.setUsername(request.getUsername());
        user = userRepository.saveAndFlush(user);

        return userRegistrationDtoFactory.makeUserRegistrationDto(user);
    }

    private Role getOrCreateDefaultRole() {
        return roleRepository.findByName(RoleName.ROLE_USER)
                .orElseGet(() -> {
                    Role defaultRole = new Role();
                    defaultRole.setName(RoleName.ROLE_USER);
                    return roleRepository.save(defaultRole);
                });
    }
}