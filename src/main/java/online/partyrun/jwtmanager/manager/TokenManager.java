package online.partyrun.jwtmanager.manager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenManager {

    Key key;
    long expireSeconds;

    public TokenManager(String key, long expireSeconds) {
        validateExpireSecond(expireSeconds);

        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        this.expireSeconds = expireSeconds;
    }

    private void validateExpireSecond(long expireSecond) {
        if (expireSecond <= 0) {
            throw new IllegalArgumentException("expire seconds는 0보다 커야합니다.");
        }
    }

    public String generate(Map<String, Object> payload) {
        validatePayload(payload);
        validateKeys(payload.keySet());
        validateValues(payload.values());
        final Claims claims = Jwts.claims(payload);
        return generateToken(claims, expireSeconds);
    }

    private void validatePayload(Map<String, Object> payload) {
        if (Objects.isNull(payload)) {
            throw new IllegalArgumentException("payload는 빈 값일 수 없습니다.");
        }
    }

    private void validateKeys(Set<String> keys) {
        if (!keys.stream().allMatch(StringUtils::hasText)) {
            throw new IllegalArgumentException("key는 빈 값일 수 없습니다.");
        }
    }

    private void validateValues(Collection<Object> values) {
        if (values.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("values는 빈 값일 수 없습니다.");
        }
    }

    private String generateToken(Claims claims, long expireSecond) {
        final LocalDateTime expireAt = LocalDateTime.now().plusSeconds(expireSecond);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims extract(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }
}
