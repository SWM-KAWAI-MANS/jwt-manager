package online.partyrun.jwtmanager;

import online.partyrun.jwtmanager.dto.JwtToken;

public interface JwtGenerator {
    JwtToken generate(String id);

    String generateAccessToken(String refreshToken);
}
