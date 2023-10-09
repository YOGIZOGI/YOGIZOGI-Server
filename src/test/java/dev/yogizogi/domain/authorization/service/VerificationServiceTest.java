package dev.yogizogi.domain.authorization.service;

import static dev.yogizogi.domain.authorization.factory.fixtures.VerificationCodeFixtures.받은_핸드폰_번호;
import static dev.yogizogi.domain.authorization.factory.fixtures.VerificationCodeFixtures.인증코드;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.authorization.factory.dto.VerificationFactory;
import dev.yogizogi.domain.user.exception.AlreadyUsePhoneNumberException;
import dev.yogizogi.domain.user.exception.UserException;
import dev.yogizogi.domain.user.service.UserService;
import dev.yogizogi.global.common.status.MessageStatus;
import dev.yogizogi.global.common.status.VerificationStatus;
import dev.yogizogi.global.util.RedisUtils;
import dev.yogizogi.infra.coolsms.CoolSmsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayName("VerificationService 비즈니스 로직 동작 테스트")
class VerificationServiceTest {

    @InjectMocks
    private VerificationService verificationService;

    @Mock
    private UserService userService;

    @Mock
    private CoolSmsService coolSmsService;

    @Mock
    private RedisUtils redisUtils;

    @Test
    void 인증번호_전송_성공() {

        // given

        // when
        given(userService.isUsePhoneNumber(eq(받은_핸드폰_번호)))
                .willReturn(false);

        given(coolSmsService.sendOne(eq(받은_핸드폰_번호)))
                .willReturn(VerificationFactory.successSingleMessageSentResponse());

        // then
        Assertions.assertThat
                        (verificationService.sendVerificationCodeForSignUp(받은_핸드폰_번호).getStatus())
                .isEqualTo(MessageStatus.SUCCESS);

    }

    @Test
    void 인증번호_전송_실패() {

        // given

        // when
        given(userService.isUsePhoneNumber(eq(받은_핸드폰_번호)))
                .willReturn(false);

        given(coolSmsService.sendOne(eq(받은_핸드폰_번호)))
                .willReturn(VerificationFactory.failSingleMessageSentResponse());

        // then
        Assertions.assertThat
                        (verificationService.sendVerificationCodeForSignUp(받은_핸드폰_번호).getStatus())
                .isEqualTo(MessageStatus.FAIL);

    }

    @Test
    void 인증번호_전송_실패_중복된_핸드폰_번호() throws UserException {

        // given

        // when
        given(userService.isUsePhoneNumber(eq(받은_핸드폰_번호)))
                .willReturn(true);

        // then
        Assertions.assertThatThrownBy
                        (() -> verificationService.sendVerificationCodeForSignUp(받은_핸드폰_번호))
                .isInstanceOf(AlreadyUsePhoneNumberException.class);

    }

    @Test
    void 인증번호_확인_성공() {

        // given
        String 받은코드 = 인증코드;

        // when
        given(redisUtils.findByKey(eq(받은_핸드폰_번호))).willReturn(인증코드);

        // then
        Assertions
                .assertThat(verificationService.checkVerificationCode(받은_핸드폰_번호, 받은코드).getStatus())
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
                .assertThat(verificationService.checkVerificationCode(받은_핸드폰_번호, 받은코드).getStatus())
                .isEqualTo(VerificationStatus.NOT_PASS);


    }

}