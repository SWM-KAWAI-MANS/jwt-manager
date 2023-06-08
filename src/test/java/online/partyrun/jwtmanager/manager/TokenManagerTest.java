package online.partyrun.jwtmanager.manager;

import io.jsonwebtoken.MalformedJwtException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import online.partyrun.jwtmanager.dto.JwtPayload;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@DisplayName("TokenManager 클래스")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class TokenManagerTest {

    String id = "1";
    String key = "asdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasd";
    long expireSeconds = 2_592_000L;
    Clock clock = Clock.systemDefaultZone();
    TokenManager tokenManager = new TokenManager(key, expireSeconds, clock);

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class tokenMaanger를_생성할_때 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class expireSeconds가_0이하라면 {

            @ParameterizedTest
            @ValueSource(longs = {-1, 0})
            @DisplayName("예외를 반환한다.")
            void throwException(long expireSeconds) {
                assertThatThrownBy(() -> new TokenManager(key, expireSeconds, clock))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class generate_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class id가_주어지면 {
            @Test
            @DisplayName("jwt 토큰을 반환한다.")
            void returnToken() {
                final String generate = tokenManager.generate(id);
                assertThat(generate.split("\\.")).hasSize(3);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class id가_비거나_null이면 {

            @ParameterizedTest
            @NullSource
            @EmptySource
            @DisplayName("예외를 반환한다.")
            void throwException(String id) {
                assertThatThrownBy(() -> tokenManager.generate(id))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class extract_메서드는 {


        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class accessToken이_주어지면 {
            String accessToken = tokenManager.generate(id);

            @Test
            @DisplayName("JwtPayload를 반환한다")
            void returnJwtPayload() {
                final JwtPayload result = tokenManager.extract(accessToken);
                assertAll(
                        () -> assertThat(result.id()).isEqualTo(id),
                        () -> assertThat(result.expireAt())
                                .isEqualTo(LocalDateTime.now(clock)
                                        .plusSeconds(expireSeconds)
                                        .truncatedTo(ChronoUnit.SECONDS))
                );
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class accessToken이_없거나_null이면 {
            @ParameterizedTest
            @NullSource
            @EmptySource
            @DisplayName("예외를 반환한다.")
            void throwException(String accessToken) {
                assertThatThrownBy(() -> tokenManager.extract(accessToken))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class accessToken이_형식이_잘못됐으면 {
            @ParameterizedTest
            @ValueSource(strings = {"qwer.adsf.zxcv","invaild token"})
            @DisplayName("예외를 반환한다.")
            void throwException(String accessToken) {
                assertThatThrownBy(() -> tokenManager.extract(accessToken))
                        .isInstanceOf(MalformedJwtException.class);
            }
        }
    }
}
