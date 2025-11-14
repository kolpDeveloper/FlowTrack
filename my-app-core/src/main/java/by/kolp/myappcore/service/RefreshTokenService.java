package by.kolp.myappcore.service;

import by.kolp.myappcore.exceptions.BadRequestException;
import by.kolp.myappcore.model.dto.RefreshTokenDTO;
import by.kolp.myappcore.model.entity.RefreshToken;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.repository.interfaces.RefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.expiration}")
    private Duration tokenExpiration;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);

        RefreshToken resultToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiresAt(Instant.now().plus(tokenExpiration))
                .build();

        return refreshTokenRepository.save(resultToken);
    }


    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new BadRequestException("Invalid refresh token"));

        if (token.isRevoked()) {
            throw new BadRequestException("Token is revoked");
        }

        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new BadRequestException("Token expired");
        }
        return token;
    }

    @Transactional
    public void revokeRefreshToken(RefreshTokenDTO refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken.refreshToken())
                .orElseThrow(() -> new BadRequestException("Invalid refresh token"));

        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }

    @Transactional
    public void cleanUpRefreshToken() {
        refreshTokenRepository.deleteExpiredToken();
        log.info("Cleaned up refresh token");
    }

}
