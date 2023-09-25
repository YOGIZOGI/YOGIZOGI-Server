package dev.yogizogi.domain.user.service;

import static dev.yogizogi.domain.user.factory.dto.CreateUserFactory.createUserInDto;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.계정;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.user.exception.DuplicatedAccountException;
import dev.yogizogi.domain.user.exception.DuplicatedNicknameException;
import dev.yogizogi.domain.user.exception.DuplicatedPhoneNumberException;
import dev.yogizogi.domain.user.exception.NotExistAccountException;
import dev.yogizogi.domain.user.exception.UserException;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateUserOutDto;
import dev.yogizogi.domain.user.model.dto.response.DeleteUserOutDto;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.status.BaseStatus;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 비즈니스 로직 동작 테스트")
class UserServiceTest {

    @InjectMocks
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
        given(userRepository.findByAccountNameAndStatus(anyString(), any(BaseStatus.class))).willReturn(Optional.empty());
        given(userRepository.findByNicknameAndStatus(anyString(), any(BaseStatus.class))).willReturn(Optional.empty());
        given(userRepository.findByPhoneNumberAndStatus(anyString(), any(BaseStatus.class))).willReturn(Optional.empty());

        // when
        CreateUserOutDto response = userService.signUp(request);

        // then
        Assertions.assertThat(request.getAccountName()).isEqualTo(response.getAccountName());

    }

    @Test
    void 회원_가입_계정_중복() throws UserException {

        // given
        CreateUserInDto request = createUserInDto();

        // mocking
        given(userRepository.findByAccountNameAndStatus(eq(request.getAccountName()), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUser()));

        // then
        Assertions.assertThatThrownBy(() -> userService.signUp(request)).isInstanceOf(DuplicatedAccountException.class);

    }

    @Test
    void 회원_가입_닉네임_중복() throws UserException {

        // given
        CreateUserInDto request = createUserInDto();

        // mocking
        given(userRepository.findByNicknameAndStatus(eq(request.getNickname()), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUser()));

        // then
        Assertions.assertThatThrownBy(() -> userService.signUp(request)).isInstanceOf(DuplicatedNicknameException.class);

    }

    @Test
    void 회원_가입_핸드폰_번호_중복() throws UserException {

        // given
        CreateUserInDto request = createUserInDto();

        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(request.getPhoneNumber()), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUser()));

        // then
        Assertions.assertThatThrownBy(() -> userService.signUp(request)).isInstanceOf(DuplicatedPhoneNumberException.class);

    }

    @Test
    void 회원_탈퇴() {

        // given
        User removeUser = UserFactory.createUser();

        // mocking
        given(userRepository.findByAccountNameAndStatus(eq(removeUser.getAccountName()), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUser()));

        // when
        DeleteUserOutDto response = userService.deleteUser(removeUser.getAccountName());

        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(BaseStatus.INACTIVE);

    }

    @Test
    void 회원_탈퇴_없는_계정() {

        // given


        // mocking
        given(userRepository.findByAccountNameAndStatus(eq(계정), eq(BaseStatus.ACTIVE))).willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(() -> userService.deleteUser(계정))
                .isInstanceOf(NotExistAccountException.class);

    }


}