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
import java.time.ZoneId;
import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenManager {
    static String ID = "id";
    static String ROLE = "role";

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

    public String generate(String id, Collection<String> roles) {
        validateId(id);
        validateRoles(roles);
        final Claims claims = getClaims(id, roles);
        return generateToken(claims, expireSeconds);
    }

    private void validateId(String id) {
        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException(String.format("%s는 빈 값일 수 없습니다.", ID));
        }
    }

    private void validateRoles(Collection<String> roles) {
        if (!roles.stream().allMatch(StringUtils::hasText)) {
            throw new IllegalArgumentException(String.format("%s은 빈 값일 수 없습니다.", ROLE));
        }
    }

    private Claims getClaims(String id, Collection<String> roles) {
        final Claims claims = Jwts.claims();
        claims.put(ID, id);
        claims.put(ROLE, roles);
        return claims;
    }

    private String generateToken(Claims claims, long expireSecond) {
        final LocalDateTime expireAt = LocalDateTime.now().plusSeconds(expireSecond);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public JwtPayload extract(String accessToken) {
        final Claims claims = parseClaims(accessToken);
        final String id = claims.get(ID, String.class);
        final List<String> roles = claims.get(ROLE, ArrayList.class);
        final LocalDateTime expireAt =
                new Timestamp(claims.getExpiration().getTime()).toLocalDateTime();
        return new JwtPayload(id, roles, expireAt);
    }

    private Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }
}
