package online.partyrun.jwtmanager.manager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import online.partyrun.jwtmanager.dto.JwtPayload;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenManager {

    private static final String ID = "id";

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

    public String generate(String id) {
        validateId(id);
        final Claims claims = getClaims(id);
        return generateToken(claims, expireSeconds);
    }

    private void validateId(String id) {
        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException("id는 빈 값 일 수 없습니다.");
        }
    }

    private Claims getClaims(String id) {
        final Claims claims = Jwts.claims();
        claims.put(ID, id);
        return claims;
    }

    private String generateToken(Claims claims, long expireSecond) {
        final LocalDateTime expireAt = LocalDateTime.now().plusSeconds(expireSecond);

        return Jwts.builder()
                .setSubject(claims.get(ID, String.class))
                .setClaims(claims)
                .setExpiration(Timestamp.valueOf(expireAt))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public JwtPayload extract(String accessToken) {
        final Claims claims = parseClaims(accessToken);
        final String id = claims.get(ID, String.class);
        final LocalDateTime expireAt = new Timestamp(claims.getExpiration().getTime()).toLocalDateTime();

        return new JwtPayload(id, expireAt);
    }

    private Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }
}
