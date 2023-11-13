package dev.yogizogi.domain.security.service;

import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰_번호;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.security.model.CustomUserDetails;
import dev.yogizogi.domain.user.exception.NotExistPhoneNumberException;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.status.BaseStatus;
import java.util.Optional;
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
@DisplayName("CustomUserDetailService 비즈니스 로직 동작 테스트")
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void 유저_불러오기() {

        // given

        // mocking
        given(userRepository
                .findByPhoneNumberAndStatus(eq(핸드폰_번호), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUserWithProfile()));

        // when
        // then
        Assertions.assertThat(customUserDetailsService.loadUserByUsername(핸드폰_번호))
                .isInstanceOf(CustomUserDetails.class);

    }

    @Test
    void 유저_불러오기_실패() {

        // given

        // mocking
        given(userRepository
                .findByPhoneNumberAndStatus(eq(핸드폰_번호), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> customUserDetailsService.loadUserByUsername(핸드폰_번호))
                .isInstanceOf(NotExistPhoneNumberException.class);

    }

}