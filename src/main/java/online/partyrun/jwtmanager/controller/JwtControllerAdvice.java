package online.partyrun.jwtmanager.controller;

import io.jsonwebtoken.JwtException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import online.partyrun.jwtmanager.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class JwtControllerAdvice {

    @PostConstruct
    public void test() {
        log.info("테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트");
    }
    @ExceptionHandler({JwtException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleJwtException(RuntimeException exception) {
        log.error("{}", exception.getMessage());
        return new ExceptionResponse("승인되지 않은 요청입니다.");
    }
}
