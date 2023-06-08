package online.partyrun.jwtmanager.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import online.partyrun.jwtmanager.manager.JwtManager;
import online.partyrun.jwtmanager.manager.TokenManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.time.Clock;

@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@Import(ClockConfig.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtConfig {

    Clock clock;

    @Bean
    public JwtManager jwtManager(@Value("${jwt.access-secret-key}") String accessKey,
                                 @Value("${jwt.access-expire-second}") Long accessExpireSecond,
                                 @Value("${jwt.refresh-secret-key}") String refreshKey,
                                 @Value("${jwt.refresh-expire-second}") Long refreshExpireSecond
    ) {
        log.info("Starting JwtManager");
        return new JwtManager(tokenManager(accessKey, accessExpireSecond), tokenManager(refreshKey, refreshExpireSecond));
    }

    private TokenManager tokenManager(String key, long tokenExpireSecond) {
        return new TokenManager(key, tokenExpireSecond, clock);
    }
}
