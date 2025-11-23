package by.kolp.user_service.repository.interfaces;

import by.kolp.user_service.model.entity.RefreshToken;
import by.kolp.user_service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query("DELETE RefreshToken rt where rt.user = :user")
    void deleteByUser(User user);

    @Modifying
    @Query("DELETE FROM RefreshToken rt where rt.expiresAt < CURRENT_TIMESTAMP")
    void deleteExpiredToken();

}
