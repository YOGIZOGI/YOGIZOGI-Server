package dev.yogizogi.domain.auth.service;

import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.리프레쉬_토큰;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.어세스_토큰;
import static dev.yogizogi.domain.auth.factory.fixtures.VerificationCodeFixtures.받은_핸드폰_번호;
import static dev.yogizogi.domain.auth.factory.fixtures.VerificationCodeFixtures.인증코드;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.암호화_비밀번호;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.auth.exception.FailLoginException;
import dev.yogizogi.domain.auth.factory.dto.LoginFactory;
import dev.yogizogi.domain.auth.factory.dto.VerificationFactory;
import dev.yogizogi.domain.auth.model.dto.request.LoginInDto;
import dev.yogizogi.domain.auth.model.dto.response.LoginOutDto;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.domain.user.exception.DuplicatedPhoneNumberException;
import dev.yogizogi.domain.user.exception.NotExistAccountException;
import dev.yogizogi.domain.user.exception.UserException;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.domain.user.service.UserService;
import dev.yogizogi.global.common.status.BaseStatus;
import dev.yogizogi.global.common.status.MessageStatus;
import dev.yogizogi.global.common.status.VerificationStatus;
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
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService 비즈니스 로직 동작 테스트")
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

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

    @Mock
    private RedisUtils redisUtils;

    @Test
    void 로그인() {

        // given
        LoginInDto request = LoginFactory.LoginInDto();

        // mocking
        given(userRepository.findByAccountNameAndStatus(eq(request.getAccountName()), eq(BaseStatus.ACTIVE))).willReturn(Optional.of(UserFactory.createUserPasswordEncrypt()));
        given(passwordEncoder.matches(eq(request.getPassword()), eq(암호화_비밀번호))).willReturn(true);
        given(jwtService.issueAccessToken(any(), eq(request.getAccountName()))).willReturn(어세스_토큰);
        given(jwtService.issueRefreshToken(any(), eq(request.getAccountName()))).willReturn(리프레쉬_토큰);

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

    @Test
    void 인증번호_전송_성공() {

        // given

        // when
        given(userService.checkPhoneNumberDuplication(eq(받은_핸드폰_번호)))
                .willReturn(false);

        given(coolSmsService.sendOne(eq(받은_핸드폰_번호), anyString()))
                .willReturn(VerificationFactory.successSingleMessageSentResponse());

        // then
        Assertions.assertThat
                (authService.sendVerificationCode(받은_핸드폰_번호).getStatus())
                .isEqualTo(MessageStatus.SUCCESS);

    }

    @Test
    void 인증번호_전송_실패() {

        // given

        // when
        given(userService.checkPhoneNumberDuplication(eq(받은_핸드폰_번호)))
                .willReturn(false);

        given(coolSmsService.sendOne(eq(받은_핸드폰_번호), anyString()))
                .willReturn(VerificationFactory.failSingleMessageSentResponse());

        // then
        Assertions.assertThat
                        (authService.sendVerificationCode(받은_핸드폰_번호).getStatus())
                .isEqualTo(MessageStatus.FAIL);

    }

    @Test
    void 인증번호_전송_실패_중복된_핸드폰_번호() throws UserException {

        // given

        // when
        given(userService.checkPhoneNumberDuplication(eq(받은_핸드폰_번호)))
                .willReturn(true);

        // then
        Assertions.assertThatThrownBy
                (() -> authService.sendVerificationCode(받은_핸드폰_번호))
                .isInstanceOf(DuplicatedPhoneNumberException.class);

    }

    @Test
    void 인증번호_확인_성공() {

        // given
        String 받은코드 = 인증코드;

        // when
        given(redisUtils.findByKey(eq(받은_핸드폰_번호))).willReturn(인증코드);

        // then
        Assertions
                .assertThat(authService.checkVerificationCode(받은_핸드폰_번호, 받은코드).getStatus())
                .isEqualTo(VerificationStatus.PASS);

    }

    @Test
    void 인증번호_확인_실패() {

        // given
        String 받은코드 = 인증코드;

        // when
        given(redisUtils.findByKey(eq(받은_핸드폰_번호))).willReturn(anyString());

        // then
        Assertions
                .assertThat(authService.checkVerificationCode(받은_핸드폰_번호, 받은코드).getStatus())
                .isEqualTo(VerificationStatus.NOT_PASS);


    }



}