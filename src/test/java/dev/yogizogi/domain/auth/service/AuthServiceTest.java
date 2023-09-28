package dev.yogizogi.domain.auth.service;

import static dev.yogizogi.domain.auth.factory.fixtures.TokenFixtures.리프레쉬_토큰;
import static dev.yogizogi.domain.auth.factory.fixtures.TokenFixtures.어세스_토큰;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.암호화_비밀번호;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.auth.exception.FailLoginException;
import dev.yogizogi.domain.auth.factory.factory.LoginFactory;
import dev.yogizogi.domain.auth.model.dto.request.LoginInDto;
import dev.yogizogi.domain.auth.model.dto.response.LoginOutDto;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.domain.user.exception.NotExistAccountException;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.status.BaseStatus;
import dev.yogizogi.global.util.RedisUtils;
import dev.yogizogi.infra.coolsms.CoolSmsService;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService 비즈니스 로직 동작 테스트")
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CoolSmsService coolSmsService;

    @Mock
    private RedisUtils redisUtils;

    @Test
    void 로그인() {

        // given
        LoginInDto request = LoginFactory.LoginInDto();

        // mocking
        given(userRepository.findByAccountNameAndStatus(eq(request.getAccountName()), eq(BaseStatus.ACTIVE))).willReturn(Optional.of(UserFactory.createUserPasswordEncrypt()));
        given(passwordEncoder.matches(eq(request.getPassword()), eq(암호화_비밀번호))).willReturn(true);
        given(jwtService.createAccessToken(any(), eq(request.getAccountName()))).willReturn(어세스_토큰);
        given(jwtService.createRefreshToken(any(), eq(request.getAccountName()))).willReturn(리프레쉬_토큰);

        // when
        LoginOutDto response = authService.login(request);

        // then
        Assertions.assertThat(response.getAccessToken()).isNotEmpty();
        Assertions.assertThat(response.getRefreshToken()).isNotEmpty();

    }

    @Test
    void 로그인_실패_존재하지_않는_계정() {

        // given
        LoginInDto request = LoginFactory.LoginInDto();

        // mocking
        given(userRepository.findByAccountNameAndStatus(eq(request.getAccountName()), eq(BaseStatus.ACTIVE))).willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(() -> authService.login(request)).isInstanceOf(NotExistAccountException.class);

    }


    @Test
    void 로그인_실패_비밀번호_불일치() {

        // given
        LoginInDto request = LoginFactory.LoginInDto();

        // mocking
        given(userRepository.findByAccountNameAndStatus(eq(request.getAccountName()), eq(BaseStatus.ACTIVE))).willReturn(Optional.of(UserFactory.createUserPasswordEncrypt()));
        given(passwordEncoder.matches(eq(request.getPassword()), eq(암호화_비밀번호))).willReturn(false);

        // when
        // then
        Assertions.assertThatThrownBy(() -> authService.login(request)).isInstanceOf(FailLoginException.class);

    }


}