package online.partyrun.jwtmanager;

import io.jsonwebtoken.Claims;

public interface JwtExtractor {
    Claims extract(String accessToken);
}
