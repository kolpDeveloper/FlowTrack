package by.kolp.myappweb.controller;

import by.kolp.myappcore.model.dto.UserCreatingRequestDTO;
import by.kolp.myappcore.model.dto.UserRegistrationDTO;
import by.kolp.myappcore.model.dto.UsernameDto;
import by.kolp.myappcore.service.RegistrationService;
import by.kolp.myappcore.service.UserService;
import by.kolp.myappweb.dto.AckDTO;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api")
@RestController
public class UserController {

    UserService userService;
    RegistrationService registrationService;


    public static final String CREATE_USER = "/user";
    public static final String EDIT_USER = "/user/{user_id}";
    public static final String FETCH_USERS = "/user";
    public static final String DELETE_USER = "/user/{user_id}";


    @DeleteMapping(value = DELETE_USER)
    public AckDTO deleteUser(@PathVariable("user_id") Integer userId) {
        userService.deleteById(userId);
        log.info("Deleted user with id {}", userId);
        return new AckDTO("User successfully deleted", true);
    }

    @GetMapping(FETCH_USERS)
    public ResponseEntity<Page<UserRegistrationDTO>> fetchUsers(@RequestParam(value = "prefix_name", required = false) String prefixName,
                                                                @PageableDefault(size = 20, sort = "username") Pageable pageable) {
        Page<UserRegistrationDTO> result = userService.fetchUser(prefixName, pageable);
        log.info(result.toString());
        return ResponseEntity.ok(result);
    }


    @PostMapping(CREATE_USER)
    public ResponseEntity<UserRegistrationDTO> register(@RequestBody @Valid UserCreatingRequestDTO request)
    {
        log.info("Request body: {}", request.username());
        return ResponseEntity.ok(registrationService.register(request));
    }

    @PatchMapping(EDIT_USER)
    public ResponseEntity<UserRegistrationDTO> editUsername(@PathVariable("user_id") Integer userId,
                                                            @RequestBody @Valid UsernameDto request)
    {
        UserRegistrationDTO savedUser = userService.edit(userId, request);
        log.info("User:{} successfully edited!", savedUser);
        return ResponseEntity.ok(savedUser);
    }
}
