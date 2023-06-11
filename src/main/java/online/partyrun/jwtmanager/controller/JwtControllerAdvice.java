package online.partyrun.jwtmanager.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@AutoConfiguration
@RestControllerAdvice(basePackages = "online.partyrun.jwtmanager")
public class JwtControllerAdvice {
    @ExceptionHandler({JwtException.class, IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> handleJwtException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse("승인되지 않은 요청입니다."));
    }
}
