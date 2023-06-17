package online.partyrun.jwtmanager.dto;

import lombok.Builder;

@Builder
public record JwtToken(String accessToken, String refreshToken) {}
