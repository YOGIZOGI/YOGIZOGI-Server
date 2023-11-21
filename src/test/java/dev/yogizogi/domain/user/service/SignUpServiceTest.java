package dev.yogizogi.domain.user.service;

import static dev.yogizogi.domain.user.factory.fixtures.PasswordFixtures.비밀번호;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰_번호;

import dev.yogizogi.domain.meokmap.repository.MeokMapRepository;
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
    private MeokMapRepository meokMapRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void 회원_가입() {

        // given
        String 받은_핸드폰_번호 = 핸드폰_번호;
        String 받은_비밀번호 = 비밀번호;

        // mocking
        // when
        CreateUserOutDto response = signUpService.signUp(받은_핸드폰_번호, 받은_비밀번호);

        // then
        Assertions.assertThat(받은_핸드폰_번호).isEqualTo(response.getPhoneNumber());

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