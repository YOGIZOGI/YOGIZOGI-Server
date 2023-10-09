package dev.yogizogi.domain.security.service;

import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.저장된_리프레쉬_토큰;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.토큰에_포함된_계정;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.토큰에_포함된_식별자;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.토큰에_포함할_계정;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.토큰에_포함할_식별자;
import static dev.yogizogi.domain.security.factory.fixtures.SecretKeyFixtures.암호화키;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.계정;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.식별자;
import static dev.yogizogi.global.common.model.constant.Format.TOKEN_PREFIX;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.authorization.model.dto.response.ReissueAccessTokenOutDto;
import dev.yogizogi.domain.security.exception.ExpiredTokenException;
import dev.yogizogi.domain.security.exception.FailToExtractSubjectException;
import dev.yogizogi.domain.security.factory.entity.SubjectFactory;
import dev.yogizogi.domain.security.model.Subject;
import dev.yogizogi.domain.security.model.TokenType;
import dev.yogizogi.domain.user.exception.NotExistAccountException;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.status.BaseStatus;
import dev.yogizogi.global.util.RedisUtils;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayName("JwtService 비즈니스 로직 동작 테스트")
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private RedisUtils redisUtils;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setSecretKey() {
        ReflectionTestUtils.setField(jwtService, "secretKey",  암호화키);
    }

    @Test
    void 어세스_토큰_발급() {

        // given

        // when
        String 발행한_토큰 = jwtService.issueAccessToken(토큰에_포함할_식별자, 토큰에_포함할_계정);

        // then
        Assertions.assertThat(발행한_토큰).startsWith(TOKEN_PREFIX);

    }

    @Test
    void 어세스_토큰_재발급() {

        // given
        User 받은_정보와_일치하는_계정 = UserFactory.createUser();

        // mocking
        given(userRepository
                .findByIdAndAccountNameAndStatus(
                        eq(식별자),
                        eq(계정),
                        eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(받은_정보와_일치하는_계정));

        given(redisUtils.findByKey(eq(토큰에_포함된_계정)))
                .willReturn(저장된_리프레쉬_토큰);

        // when
        ReissueAccessTokenOutDto res = jwtService.reissueAccessToken(식별자, 계정);

        // then
        Assertions.assertThat(res.getId()).isEqualTo(토큰에_포함된_식별자);
        Assertions.assertThat(res.getAccessToken()).startsWith(TOKEN_PREFIX);

    }

    @Test
    void 어세스_토큰_재발급_실패_계정_없음() {

        // given
        // mocking
        given(userRepository
                .findByIdAndAccountNameAndStatus(
                        eq(식별자),
                        eq(계정),
                        eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy
                (() -> jwtService.reissueAccessToken(식별자, 계정)).isInstanceOf(NotExistAccountException.class);

    }

    @Test
    void 어세스_토큰_재발급_실패_리프레쉬_토큰_없음() {

        // given
        User 받은_정보와_일치하는_계정 = UserFactory.createUser();

        // mocking
        given(userRepository
                .findByIdAndAccountNameAndStatus(
                        eq(식별자),
                        eq(계정),
                        eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(받은_정보와_일치하는_계정));

        given(redisUtils.findByKey(eq(토큰에_포함된_계정)))
                .willReturn(null);

        // when
        // then
        Assertions.assertThatThrownBy
                (() -> jwtService.reissueAccessToken(식별자, 계정)).isInstanceOf(ExpiredTokenException.class);

    }

    @Test
    void 리프레쉬_토큰_발급() {

        // given
        // when
        String 발행한_리프레쉬_토큰 = jwtService.issueRefreshToken(토큰에_포함할_식별자, 토큰에_포함할_계정);

        // then
        Assertions.assertThat(발행한_리프레쉬_토큰).startsWith(TOKEN_PREFIX);

    }

    @Test
    void 토큰_서브젝트_추출() {

        // given
        String 토큰 = jwtService.issueAccessToken(식별자, 계정).replace(TOKEN_PREFIX, "");

        // mocking
        given(jwtService.extractSubject(토큰))
                .willReturn(SubjectFactory.extractSubject());

        // when
        Subject 추출된_서브젝트 = jwtService.extractSubject(토큰);

        // then
        Assertions.assertThat(추출된_서브젝트.getId()).isEqualTo(식별자);
        Assertions.assertThat(추출된_서브젝트.getAccountName()).isEqualTo(계정);
        Assertions.assertThat(추출된_서브젝트.getType()).isEqualTo(TokenType.ACCESS_TOKEN);

    }

    @Test
    void 토큰_서브젝트_추출_실패() {

        // given
        String 토큰 = jwtService.issueAccessToken(식별자, 계정).replace(TOKEN_PREFIX, "");

        // mocking
        given(jwtService.extractSubject(토큰))
                .willThrow(JsonProcessingException.class);

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> jwtService.extractSubject(토큰))
                .isInstanceOf(FailToExtractSubjectException.class);

    }



}