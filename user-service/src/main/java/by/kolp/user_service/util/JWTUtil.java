package by.kolp.user_service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:PT1H}")
    @Getter
    private Duration tokenExpiration;

    public String generateToken(String username) {

        Date expiredDate = Date.from(Instant.now().plus(tokenExpiration));

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(Instant.now())
                .withExpiresAt(expiredDate)
                .sign(Algorithm.HMAC256(secret));
    }
}
