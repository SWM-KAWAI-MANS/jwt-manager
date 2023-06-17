package online.partyrun.jwtmanager.manager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import online.partyrun.jwtmanager.dto.JwtToken;

import org.junit.jupiter.api.*;

@DisplayName("JwtManagerTest 클래스")
@FieldDefaults(level = AccessLevel.PRIVATE)
class JwtManagerTest {
    long accessExpireSeconds = 2_592_000;
    long refreshExpireSeconds = 7_776_000;
    String accessKey =
            "accessdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasd";
    String refreshKey =
            "refreshaasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdadasdasdasdadasdasdasdasdadasd";
    TokenManager accessTokenManager = new TokenManager(accessKey, accessExpireSeconds);
    TokenManager refreshTokenManager = new TokenManager(refreshKey, refreshExpireSeconds);

    JwtManager jwtManager = new JwtManager(accessTokenManager, refreshTokenManager);

    String id = "박현준";
    String role1 = "admin";
    String role2 = "user";

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class generate_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class id와_role이_주어지면 {

            @Test
            @DisplayName("JwtToken을 반환한다.")
            void returnJwtToken() {
                final JwtToken result = jwtManager.generate(id, role1, role2);
                assertAll(
                        () -> assertThat(result.accessToken()).isNotBlank(),
                        () -> assertThat(result.refreshToken()).isNotBlank());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class role이_주어지지_않아도 {

            @Test
            @DisplayName("JwtToken을 반환한다.")
            void returnJwtToken() {
                final JwtToken result = jwtManager.generate(id);
                System.out.println(result.accessToken());
                assertAll(
                        () -> assertThat(result.accessToken()).isNotBlank(),
                        () -> assertThat(result.refreshToken()).isNotBlank());
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
                assertThat(accessToken).isNotBlank();
            }
        }
    }
}
