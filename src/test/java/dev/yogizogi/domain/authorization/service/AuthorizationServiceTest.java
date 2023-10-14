package dev.yogizogi.domain.authorization.service;

import static dev.yogizogi.domain.authorization.factory.fixtures.LoginFixtures.받은_비밀번호;
import static dev.yogizogi.domain.authorization.factory.fixtures.LoginFixtures.받은_핸드폰_번호;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.리프레쉬_토큰;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.어세스_토큰;
import static dev.yogizogi.domain.user.factory.fixtures.PasswordFixtures.암호화_비밀번호;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.authorization.exception.FailLoginException;
import dev.yogizogi.domain.authorization.model.dto.response.LoginOutDto;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.domain.user.exception.NotExistPhoneNumberException;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.entity.FirstLoginStatus;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.domain.user.service.UserService;
import dev.yogizogi.global.common.status.BaseStatus;
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
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService 비즈니스 로직 동작 테스트")
class AuthorizationServiceTest {

    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private CoolSmsService coolSmsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void 로그인() {

        // given
        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(받은_핸드폰_번호), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUserPasswordEncrypt()));
        given(passwordEncoder.matches(eq(받은_비밀번호), eq(암호화_비밀번호))).willReturn(true);
        given(jwtService.issueAccessToken(any(), eq(받은_핸드폰_번호))).willReturn(어세스_토큰);
        given(jwtService.issueRefreshToken(any(), eq(받은_핸드폰_번호))).willReturn(리프레쉬_토큰);

        // when
        LoginOutDto response = authorizationService.login(받은_핸드폰_번호, 받은_비밀번호);

        // then
        Assertions.assertThat(response.getFirstLoginStatus()).isEqualTo(FirstLoginStatus.ACTIVE);
        Assertions.assertThat(response.getAccessToken()).isNotEmpty();
        Assertions.assertThat(response.getRefreshToken()).isNotEmpty();

    }

    @Test
    void 로그인_실패_존재하지_않는_계정() {

        // given
        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(받은_핸드폰_번호), eq(BaseStatus.ACTIVE))).willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(() -> authorizationService.login(받은_핸드폰_번호, 받은_비밀번호)).isInstanceOf(
                NotExistPhoneNumberException.class);

    }


    @Test
    void 로그인_실패_비밀번호_불일치() {

        // given
        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(받은_핸드폰_번호), eq(BaseStatus.ACTIVE))).willReturn(Optional.of(UserFactory.createUserPasswordEncrypt()));
        given(passwordEncoder.matches(eq(받은_비밀번호), eq(암호화_비밀번호))).willReturn(false);

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> authorizationService.login(받은_핸드폰_번호, 받은_비밀번호)).isInstanceOf(FailLoginException.class);

    }



}