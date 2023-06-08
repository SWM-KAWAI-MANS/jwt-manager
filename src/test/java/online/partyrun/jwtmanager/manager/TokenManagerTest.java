package online.partyrun.jwtmanager.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Clock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@DisplayName("TokenManager 클래스")
class TokenManagerTest {

    Clock clock = Clock.systemDefaultZone();
    String key = "asdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasd";

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class tokenMaanger를_생성할_때 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class expireSeconds가_0이하라면 {

            @ParameterizedTest
            @ValueSource(longs = {-1, 0})
            @DisplayName("예외를 반환한다.")
            void throwException(long expireSecond) {
                assertThatThrownBy(() -> new TokenManager(key, expireSecond, clock))
                      .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class generate_메서드는 {
        long expireSecond = 1000L;
        TokenManager tokenManager = new TokenManager(key, expireSecond, clock);

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
        class id가_주어지면 {
            String id = "1";

            @Test
            @DisplayName("jwt 토큰을 반환한다.")
            void returnToken() {
                final String generate = tokenManager.generate(id);
                assertThat(generate.split("\\.")).hasSize(3);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
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
}