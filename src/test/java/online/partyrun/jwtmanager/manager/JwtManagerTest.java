package online.partyrun.jwtmanager.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import online.partyrun.jwtmanager.dto.JwtToken;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("JwtManagerTest 클래스")
@FieldDefaults(level = AccessLevel.PRIVATE)
class JwtManagerTest {
    long accessExpireSeconds = 2_592_000;
    long refreshExpireSeconds = 7_776_000;
    String accessKey = "accessdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasd";
    String refreshKey = "refreshaasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdasdasdasdadasd";
    TokenManager accessTokenManager = new TokenManager(accessKey,accessExpireSeconds);
    TokenManager refreshTokenManager = new TokenManager(refreshKey,refreshExpireSeconds);

    JwtManager jwtManager = new JwtManager(accessTokenManager, refreshTokenManager);
    Map<String, Object> payload = Map.of("id", "박현준", "role", "admin");

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class generate_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class payload가_주어지면 {

            @Test
            @DisplayName("JwtToken을 반환한다.")
            void returnJwtToken() {
                final JwtToken result = jwtManager.generate(payload);
                assertAll(
                        () -> assertThat(result.accessToken()).isNotBlank(),
                        () -> assertThat(result.refreshToken()).isNotBlank()
                );
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class generateAccessToken_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class refreshToken이_주어지면 {
            final String refreshToken = jwtManager.generate(payload).refreshToken();

            @Test
            @DisplayName("access token을 반환한다")
            void returnAccessToken() {
                final String accessToken = jwtManager.generateAccessToken(refreshToken);
                assertThat(accessToken).isNotBlank();
            }
        }
    }
}
