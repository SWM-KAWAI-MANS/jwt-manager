package online.partyrun.jwtmanager.manager;

import io.jsonwebtoken.Claims;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.jwtmanager.JwtExtractor;
import online.partyrun.jwtmanager.JwtGenerator;
import online.partyrun.jwtmanager.dto.JwtToken;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JwtManager implements JwtGenerator, JwtExtractor {

    TokenManager accessTokenManager;
    TokenManager refreshTokenManager;

    @Override
    public JwtToken generate(Map<String, Object> payload) {
        return JwtToken.builder()
                .accessToken(accessTokenManager.generate(payload))
                .refreshToken(refreshTokenManager.generate(payload))
                .build();
    }

    @Override
    public Claims extract(String accessToken) {
        return accessTokenManager.extract(accessToken);
    }

    @Override
    public String generateAccessToken(String refreshToken) {
        final Map<String, Object> extract = refreshTokenManager.extract(refreshToken);
        return accessTokenManager.generate(extract);
    }
}
