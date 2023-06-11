package online.partyrun.jwtmanager.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@AutoConfiguration
public class JwtControllerAdvice {
    @ExceptionHandler({
            MalformedJwtException.class,
            SecurityException.class,
            ExpiredJwtException.class,
            UnsupportedJwtException.class
    })
    public ResponseEntity<ExceptionResponse> handleJwtException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getMessage()));
    }
}
