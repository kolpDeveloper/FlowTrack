package by.kolp.user_service.sheduler;


import by.kolp.user_service.service.RefreshTokenService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenCleanUpScheduler {

    private final RefreshTokenService refreshTokenService;

    public TokenCleanUpScheduler(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @Scheduled(cron = "0 0 */12 * * *")
    public void cleanUpExpiredTokens() {
        refreshTokenService.cleanUpRefreshToken();
    }
}
