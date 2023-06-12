package online.partyrun.jwtmanager.controller;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@AutoConfiguration
@RestControllerAdvice(basePackages = "online.partyrun.jwtmanager")
public class JwtControllerAdvice {
    @ExceptionHandler({JwtException.class, IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> handleJwtException(RuntimeException exception) {
        log.error("{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse("승인되지 않은 요청입니다."));
    }
}

package online.partyrun.jwtmanager.controller;

import io.jsonwebtoken.JwtException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@AutoConfiguration
@RestControllerAdvice(basePackages = "online.partyrun.jwtmanager")
public class JwtControllerAdvice {
    @ExceptionHandler({JwtException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleJwtException(RuntimeException exception) {
        log.error("{}", exception.getMessage());
        return new ExceptionResponse("승인되지 않은 요청입니다.");
    }
}
