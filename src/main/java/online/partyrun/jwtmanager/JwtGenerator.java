package online.partyrun.jwtmanager;

import online.partyrun.jwtmanager.dto.JwtToken;

import java.util.Map;

public interface JwtGenerator {
    JwtToken generate(Map<String, Object> items);

    String generateAccessToken(String refreshToken);
}
