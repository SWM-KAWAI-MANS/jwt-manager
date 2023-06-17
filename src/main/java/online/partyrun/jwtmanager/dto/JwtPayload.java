package online.partyrun.jwtmanager.dto;

import java.time.LocalDateTime;
import java.util.List;
public record JwtPayload(String id, List<String> roles, LocalDateTime expireAt) {
}
