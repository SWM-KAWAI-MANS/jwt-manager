package online.partyrun.jwtmanager;

import online.partyrun.jwtmanager.dto.JwtPayload;

public interface JwtExtractor {
    JwtPayload extract(String accessToken);
}
