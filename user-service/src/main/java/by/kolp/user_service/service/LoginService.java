package by.kolp.user_service.service;


import by.kolp.commonexceptions.exceptions.BadRequestException;
import by.kolp.user_service.model.dto.AuthResponseDTO;
import by.kolp.user_service.model.dto.LoginRequestDTO;
import by.kolp.user_service.model.dto.RefreshTokenDTO;
import by.kolp.user_service.model.entity.RefreshToken;
import by.kolp.user_service.model.entity.User;
import by.kolp.user_service.repository.interfaces.UserRepository;
import by.kolp.user_service.util.JWTUtil;
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
                .orElseThrow(() -> new BadRequestException("Invalid username or password!"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadRequestException("Invalid password!");
        }
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

    public AuthResponseDTO refreshToken(RefreshTokenDTO refreshTokenRequest) {

        RefreshToken token = refreshTokenService.verifyRefreshToken(refreshTokenRequest.refreshToken());
        User user = token.getUser();

        String newAccessToken = jwtUtil.generateToken(user.getUsername());
        Long expiresIn = jwtUtil.getTokenExpiration().getSeconds();


        return new AuthResponseDTO(newAccessToken, expiresIn, user.getUsername(), user.getRole().name(), token.getToken());
    }
}
