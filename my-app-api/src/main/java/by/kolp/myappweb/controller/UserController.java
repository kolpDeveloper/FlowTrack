package by.kolp.myappweb.controller;

import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.service.RegistrationService;
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
    UserMapper userMapper;
    RegistrationService registrationService;


    public static final String CREATE_USER = "/user";
    public static final String EDIT_USER = "/user/{user_id}";
    public static final String FETCH_USERS = "/user";
    public static final String DELETE_USER = "/user/{user_id}";


    @DeleteMapping(value = DELETE_USER)
    public AckDTO deleteUser(@PathVariable("user_id") Integer userId) {
        User user = userService.findById(userId);
        userService.deleteById(user.getId());
        return new AckDTO("User successfully deleted", true);
    }

    @GetMapping(FETCH_USERS)
    public ResponseEntity<Page<UserRegistrationDTO>> fetchUsers(@RequestParam(value = "prefix_name", required = false) String prefixName,
                                                @PageableDefault(size = 20, sort = "username") Pageable pageable) {

        Page<User> users =  (prefixName != null && !prefixName.isBlank()
        ? (userService.findAllByPrefix(prefixName, pageable))
                : userService.findAll(pageable));

        Page<UserRegistrationDTO> result = users.map(userMapper::toRegistrationDTO);
        return ResponseEntity.ok(result);
    }


    @PostMapping(CREATE_USER)
    public ResponseEntity<String> register(@RequestBody @Valid UserCreatingRequestDTO request) {
        User newUser = userMapper.toUser(request);
        registrationService.register(newUser);
        return ResponseEntity.ok("User successfully registered!");
    }

    @PatchMapping(EDIT_USER)
    public ResponseEntity<UserRegistrationDTO> editUsername(@PathVariable("user_id") Integer userId,
                                            @RequestBody @Valid UserCreatingRequestDTO request) {


        User savedUser  = userService.edit(userId, request.username());
        return ResponseEntity.ok(userMapper.toRegistrationDTO(savedUser));
    }
}
