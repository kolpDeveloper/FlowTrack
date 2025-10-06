package by.kolp.myappweb.controller;

import by.kolp.myappcore.exceptions.BadRequestException;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.service.UserService;
import by.kolp.myappweb.dto.AckDTO;
import by.kolp.myappweb.dto.UserCreatingRequestDTO;
import by.kolp.myappweb.dto.UserRegistrationDTO;
import by.kolp.myappweb.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
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


    UserService userService;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;


    public static final String CREATE_USER = "/user";
    public static final String EDIT_USER = "/user/{user_id}";
    public static final String FETCH_USERS = "/user";
    public static final String DELETE_USER = "/user/{user_id}";


    @DeleteMapping(value = DELETE_USER)
    public AckDTO deleteUser(@PathVariable("user_id") Integer userId) {
        User user = userService
                .findById(userId);


        userService.deleteById(user.getId());
        return new AckDTO("User successfully deleted", true);
    }

    @GetMapping(FETCH_USERS)
    public List<UserRegistrationDTO> fetchUsers(@RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.isEmpty());

        Stream<User> users = optionalPrefixName.stream()
                .map(userService::streamAllByUsernameStartingWithIgnoreCase)
                .findAny().orElseGet(userService::streamAll);

        return users
                .map(userMapper::toRegistrationDTO)
                .collect(Collectors.toList());
    }


    @PostMapping(CREATE_USER)
    public ResponseEntity<String> register(@RequestBody @Valid UserCreatingRequestDTO request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Request body is invalid");
        }

        if (request.username().isEmpty() || request.password().isEmpty()) {
            throw new BadRequestException("Username or password cannot be empty");
        }

        userService
                .findByUsername(request.username())
                .ifPresent(
                        user -> {
                            throw new BadRequestException(format("User \"%s\" already exists.", request.username()));
                        });


        User newUser = userMapper.toUser(request);
        newUser.setPassword(passwordEncoder.encode(request.password()));


        newUser = userService.save(newUser);
        return ResponseEntity.ok("User successfully registered.");
    }

    @PatchMapping(EDIT_USER)
    public UserRegistrationDTO editUsername(@PathVariable("user_id") Integer userId,
                                            @RequestBody UserCreatingRequestDTO request) {

        if (request.username().isEmpty()) {
            throw new BadRequestException("Username cannot be empty");
        }

        User user = userService.findById(userId);

        userService.findByUsername(request.username())
                .filter(anotherUser -> !Objects.equals(anotherUser.getId(), userId))
                .ifPresent(anotherUser -> {
                    throw new BadRequestException(format("User \"%s\" already exists.", request.username()));
                });

        user.setUsername(request.username());
        user = userService.saveAndFlush(user);

        return userMapper.toRegistrationDTO(user);
    }
}
