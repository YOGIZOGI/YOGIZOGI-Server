package dev.yogizogi.domain.user.service;

import static dev.yogizogi.domain.user.factory.dto.CreateUserFactory.createUserInDto;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.비밀번호;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.user.exception.AlreadyUseAccountException;
import dev.yogizogi.domain.user.exception.AlreadyUseNicknameException;
import dev.yogizogi.domain.user.exception.AlreadyUsePhoneNumberException;
import dev.yogizogi.domain.user.exception.UserException;
import dev.yogizogi.domain.user.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateUserOutDto;
import dev.yogizogi.domain.user.repository.UserRepository;
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
@DisplayName("SignUpService 비즈니스 로직 동작 테스트")
class SignUpServiceTest {

    @InjectMocks
    private SignUpService signUpService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void 회원_가입() {

        // given
        CreateUserInDto request = createUserInDto();

        // mocking
        given(userService.isUsedAccountName(request.getAccountName())).willReturn(false);
        given(userService.isUsedNickname(request.getNickname())).willReturn(false);
        given(userService.isUsePhoneNumber(request.getPhoneNumber())).willReturn(false);

        // when
        CreateUserOutDto response = signUpService.signUp(request);

        // then
        Assertions.assertThat(request.getAccountName()).isEqualTo(response.getAccountName());

    }

    @Test
    void 계정_중복() throws UserException {

        // given
        CreateUserInDto request = createUserInDto();

        // mocking
        given(userService.isUsedAccountName(request.getAccountName())).willReturn(true);

        // then
        Assertions.assertThatThrownBy(() -> signUpService.signUp(request)).isInstanceOf(AlreadyUseAccountException.class);

    }

    @Test
    void 닉네임_중복() throws UserException {

        // given
        CreateUserInDto request = createUserInDto();

        // mocking
        given(userService.isUsedNickname(request.getNickname())).willReturn(true);

        // then
        Assertions.assertThatThrownBy(() -> signUpService.signUp(request)).isInstanceOf(AlreadyUseNicknameException.class);

    }

    @Test
    void 핸드폰_번호_중복() throws UserException {

        // given
        CreateUserInDto request = createUserInDto();

        // mocking
        given(userService.isUsePhoneNumber(request.getPhoneNumber())).willReturn(true);

        // then
        Assertions.assertThatThrownBy(() -> signUpService.signUp(request)).isInstanceOf(AlreadyUsePhoneNumberException.class);

    }

    @Test
    void 비밀번호_암호화() {

        // given
        String password = 비밀번호;

        // when
        String encode = passwordEncoder.encode(password);

        // then
        Assertions.assertThat(passwordEncoder.matches(password, encode)).isFalse();

    }



}