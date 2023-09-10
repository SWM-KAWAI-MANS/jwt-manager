package online.partyrun.jwtmanager;

import online.partyrun.jwtmanager.dto.JwtPayload;

public interface JwtExtractor {
    JwtPayload extractAccessToken(String accessToken);
    JwtPayload extractRefreshToken(String refreshToken);
}
