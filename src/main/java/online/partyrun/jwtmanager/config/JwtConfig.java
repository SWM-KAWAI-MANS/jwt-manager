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

@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtConfig {

    @Bean
    public JwtManager jwtManager(
            @Value("${jwt.access-secret-key}") String accessKey,
            @Value("${jwt.access-expire-second:#{'1'}}") Long accessExpireSecond,
            @Value("${jwt.refresh-secret-key:#{'DefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefaultDefault'}}") String refreshKey,
            @Value("${jwt.refresh-expire-second:#{'1'}}") Long refreshExpireSecond) {
        log.info("Starting JwtManager");
        return new JwtManager(
                tokenManager(accessKey, accessExpireSecond),
                tokenManager(refreshKey, refreshExpireSecond));
    }

    private TokenManager tokenManager(String key, long tokenExpireSecond) {
        return new TokenManager(key, tokenExpireSecond);
    }
}
