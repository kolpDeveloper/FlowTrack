package by.kolp.myappweb.controller;

import by.kolp.myappcore.model.dto.UserCreatingRequestDTO;
import by.kolp.myappcore.service.RegistrationService;
import by.kolp.myappweb.security.JWTUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class AuthController {

    private final JWTUtil jwtUtil;
    private final RegistrationService registrationService;


    @PostMapping("/auth/registration")
    public Map<@NotNull String, @NotNull String> performRegistration(@RequestBody @Valid UserCreatingRequestDTO registration) {

        registrationService.register(registration);

        String token = jwtUtil.generateToken(registration.username());
        return Map.of("jwt-token", token);
    }
}
