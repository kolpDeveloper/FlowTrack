package by.kolp.apigateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JWTUtils {

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

    public boolean isTokenValid(String token) throws JWTVerificationException {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .build();

            DecodedJWT decode = verifier.verify(token);
            decode.getSubject();
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }

    public String  extractUserId(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decode = verifier.verify(token);
        return decode.getSubject();
    }

    public String extractRole(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decode = verifier.verify(token);
        return decode.getClaim("role").asString();
    }
}

