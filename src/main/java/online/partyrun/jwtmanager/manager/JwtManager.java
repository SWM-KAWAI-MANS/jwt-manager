package online.partyrun.jwtmanager.manager;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.jwtmanager.JwtExtractor;
import online.partyrun.jwtmanager.JwtGenerator;
import online.partyrun.jwtmanager.dto.JwtPayload;
import online.partyrun.jwtmanager.dto.JwtToken;

import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JwtManager implements JwtGenerator, JwtExtractor {

    TokenManager accessTokenManager;
    TokenManager refreshTokenManager;

    @Override
    public JwtToken generate(String id, String... roles) {
        final Set<String> nonDuplicatedRoles = Set.of(roles);

        return JwtToken.builder()
                .accessToken(accessTokenManager.generate(id, nonDuplicatedRoles))
                .refreshToken(refreshTokenManager.generate(id, nonDuplicatedRoles))
                .build();
    }

    @Override
    public JwtPayload extract(String accessToken) {
        return accessTokenManager.extract(accessToken);
    }

    @Override
    public String generateAccessToken(String refreshToken) {
        final JwtPayload jwtPayload = refreshTokenManager.extract(refreshToken);
        return accessTokenManager.generate(jwtPayload.id(), jwtPayload.roles());
    }
}
