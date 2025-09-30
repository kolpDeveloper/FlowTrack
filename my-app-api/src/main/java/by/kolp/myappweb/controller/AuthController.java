package by.kolp.myappweb.controller;

import by.kolp.myappweb.mapper.UserMapper;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.service.RegistrationService;
import by.kolp.myappcore.service.UserService;
import by.kolp.myappweb.dto.UserCreatingRequestDTO;
import by.kolp.myappweb.mapper.UserMapperImpl;
import by.kolp.myappweb.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserMapperImpl userMapper;
    private final JWTUtil jwtUtil;
    private final RegistrationService registrationService;
    //private final UserService userService;


    @PostMapping("/registration")
    public Map<@NotNull String, @NotNull String> performRegistration(@RequestBody @Valid UserCreatingRequestDTO registration, BindingResult bindingResult) {
        User user = userMapper.toUser(registration);


        if (bindingResult.hasErrors()) {
            return Map.of("ERROR", "Results has errors");
        }

        /*if (userService.findByUsername(user.getUsername()).isPresent()) {
            return Map.of("ERROR", "User already exists");
        }*/

        registrationService.register(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("jwt-token", token);
    }
}
