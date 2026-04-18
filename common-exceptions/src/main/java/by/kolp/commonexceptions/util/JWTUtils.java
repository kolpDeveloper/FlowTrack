package by.kolp.commonexceptions.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @Value("${jwt.expiration:PT1H}")
    @Getter
    private Duration tokenExpiration;

    @PostConstruct
    public void init(){
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).build();
    }

    public String generateToken(String userId, String username, String role) {

        Date expiredDate = Date.from(Instant.now().plus(tokenExpiration));

        return JWT.create()
                .withSubject(userId)
                .withClaim("username", username)
                .withClaim("role", role)
                .withIssuedAt(Instant.now())
                .withExpiresAt(expiredDate)
                .sign(algorithm);
    }

    public boolean isTokenValid(String token){
        try {
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException e){
            log.warn("JWT verification failed, token is not valid");
            return false;
        }
    }

    public String  extractUserId(String token) throws JWTVerificationException {
        DecodedJWT decode = verifier.verify(token);
        return decode.getSubject();
    }

    public String extractRole(String token) throws JWTVerificationException {
        DecodedJWT decode = verifier.verify(token);
        return decode.getClaim("role").asString();
    }

    public String extractUsername(String token) throws JWTVerificationException {
        DecodedJWT decode = verifier.verify(token);
        return decode.getClaim("username").asString();
    }
}

