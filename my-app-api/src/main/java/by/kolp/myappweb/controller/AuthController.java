package by.kolp.myappweb.controller;

import by.kolp.myappcore.jwt.JWTUtil;
import by.kolp.myappcore.model.dto.AuthResponseDTO;
import by.kolp.myappcore.model.dto.LoginRequestDTO;
import by.kolp.myappcore.model.dto.RefreshTokenDTO;
import by.kolp.myappcore.model.dto.UserCreatingRequestDTO;
import by.kolp.myappcore.service.LoginService;
import by.kolp.myappcore.service.RefreshTokenService;
import by.kolp.myappcore.service.RegistrationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping()
public class AuthController {

    private final JWTUtil jwtUtil;
    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final RefreshTokenService refreshTokenService;


    @PostMapping("/auth/registration")
    @Transactional
    public Map<@NotNull String, @NotNull String> performRegistration(@RequestBody @Valid UserCreatingRequestDTO registration) {

        registrationService.register(registration);

        String token = jwtUtil.generateToken(registration.username());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/auth/login")
    public AuthResponseDTO performLogin(@RequestBody @Valid LoginRequestDTO request) {
        return loginService.login(request);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshTokenDTO refreshTokenRequest) {
        log.info("Refreshing token!");
        return ResponseEntity.ok(loginService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/auth/revoke")
    public void revokeToken(@RequestBody RefreshTokenDTO token) {
        log.info("Revoking token!");
        refreshTokenService.revokeRefreshToken(token);
    }
}
