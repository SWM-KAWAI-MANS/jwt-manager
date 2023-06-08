package online.partyrun.jwtmanager.dto;

import java.time.LocalDateTime;

public record JwtPayload(String id, LocalDateTime expireAt) {
}
