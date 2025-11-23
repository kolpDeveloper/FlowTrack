package by.kolp.user_service.controller;


import by.kolp.user_service.model.dto.UserCreatingRequestDTO;
import by.kolp.user_service.model.dto.UserRegistrationDTO;
import by.kolp.user_service.model.dto.UsernameDto;
import by.kolp.user_service.service.RegistrationService;
import by.kolp.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class UserController {

    UserService userService;
    RegistrationService registrationService;


    public static final String CREATE_USER = "/api/user";
    public static final String EDIT_USER = "/api/user/{user_id}";
    public static final String FETCH_USERS = "/api/user";
    public static final String DELETE_USER = "/api/user/{user_id}";


    @DeleteMapping(value = DELETE_USER)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(@PathVariable("user_id") Integer userId) {
        userService.deleteById(userId);
        log.info("Deleted user with id {}", userId);
    }

    @GetMapping(FETCH_USERS)
    public ResponseEntity<Page<UserRegistrationDTO>> fetchUsers(@RequestParam(value = "prefix_name", required = false, defaultValue = "") String prefixName, @PageableDefault(size = 20, sort = "username") Pageable pageable) {
        Page<UserRegistrationDTO> result = userService.fetchUser(prefixName, pageable);
        log.info(result.toString());
        return ResponseEntity.ok(result);
    }


    @PostMapping(CREATE_USER)
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid UserCreatingRequestDTO request) {
        registrationService.register(request);
        log.info("Created username: {}", request.username());
    }

    @PatchMapping(EDIT_USER)
    public ResponseEntity<UserRegistrationDTO> editUsername(@PathVariable("user_id") Integer userId, @RequestBody @Valid UsernameDto request) {
        UserRegistrationDTO savedUser = userService.edit(userId, request);
        log.info("User:{} successfully edited!", savedUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedUser);
    }
}
