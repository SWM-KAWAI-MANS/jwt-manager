package online.partyrun.jwtmanager;

import online.partyrun.jwtmanager.dto.JwtToken;

import java.util.Set;

public interface JwtGenerator {
    JwtToken generate(String id, Set<String> roles);

    JwtToken refresh(String refreshToken);
}
