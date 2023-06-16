package online.partyrun.jwtmanager.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import online.partyrun.jwtmanager.dto.JwtToken;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("JwtManagerTest 클래스")
@FieldDefaults(level = AccessLevel.PRIVATE)
class JwtManagerTest {
    String accessKey = "accessdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasd";
    long accessExpireSeconds = 2_592_000;
    String refreshKey = "refreshaasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdasdasdasdadasd";
    long refreshExpireSeconds = 7_776_000;
    TokenManager accessTokenManager = new TokenManager(accessKey,accessExpireSeconds);
    TokenManager refreshTokenManager = new TokenManager(refreshKey,refreshExpireSeconds);

    JwtManager jwtManager = new JwtManager(accessTokenManager, refreshTokenManager);
    String id = "asdf";

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class generate_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class id가_주어지면 {

            @Test
            @DisplayName("JwtToken을 반환한다.")
            void returnJwtToken() {
                final JwtToken result = jwtManager.generate(id);
                assertAll(
                        () -> assertThat(result.accessToken()).isNotNull(),
                        () -> assertThat(result.refreshToken()).isNotNull()
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
            final String refreshToken = jwtManager.generate(id).refreshToken();

            @Test
            @DisplayName("access token을 반환한다")
            void returnAccessToken() {
                final String accessToken = jwtManager.generateAccessToken(refreshToken);
                assertThat(accessToken).isNotNull();
            }
        }
    }
}
