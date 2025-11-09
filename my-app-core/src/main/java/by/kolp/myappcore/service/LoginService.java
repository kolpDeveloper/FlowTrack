package by.kolp.myappcore.service;

import by.kolp.myappcore.exceptions.BadRequestException;
import by.kolp.myappcore.jwt.JWTUtil;
import by.kolp.myappcore.model.dto.AuthResponseDTO;
import by.kolp.myappcore.model.dto.LoginRequestDTO;
import by.kolp.myappcore.model.entity.RefreshToken;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.repository.interfaces.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public LoginService(PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService, JWTUtil jwtUtil, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Transactional
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new BadRequestException("Username already exists!"));

    user.setLastLoginAt(Instant.now());
    userRepository.save(user);

    String accessToken = jwtUtil.generateToken(loginRequest.username());
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
    Long expiresIn = jwtUtil.getTokenExpiration().getSeconds();

        return new AuthResponseDTO(
                accessToken,
                expiresIn,
                user.getUsername(),
                user.getRole().name(),
                refreshToken.getToken()
        );
    }
}
