package dev.yogizogi.domain.user.service;

import static dev.yogizogi.domain.user.factory.dto.CreateUserFactory.createUserInDto;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.계정;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.user.exception.DuplicatedAccountException;
import dev.yogizogi.domain.user.exception.DuplicatedNicknameException;
import dev.yogizogi.domain.user.exception.DuplicatedPhoneNumberException;
import dev.yogizogi.domain.user.exception.NotExistAccountException;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.user.model.dto.response.DeleteUserOutDto;
import dev.yogizogi.domain.user.model.entity.User;
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
import dev.yogizogi.domain.user.exception.UserException;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 비즈니스 로직 동작 테스트")
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void 계정_중복() throws UserException {

        // given
        CreateUserInDto req = createUserInDto();

        // mocking
        given(userRepository.findByAccountNameAndStatus(eq(req.getAccountName()), eq(BaseStatus.ACTIVE))).willReturn(Optional.of(UserFactory.createUser()));

        // then
        Assertions.assertThat(userService.checkAccountNameDuplication(req.getAccountName())).isEqualTo(true);

    }

    @Test
    void 닉네임_중복() throws UserException {

        // given
        CreateUserInDto req = createUserInDto();

        // mocking
        given(userRepository.findByNicknameAndStatus(eq(req.getNickname()), eq(BaseStatus.ACTIVE))).willReturn(Optional.of(UserFactory.createUser()));

        // then
        Assertions.assertThat(userService.checkNicknameDuplication(req.getNickname())).isEqualTo(true);

    }

    @Test
    void 핸드폰_번호_중복() throws UserException {

        // given
        CreateUserInDto req = createUserInDto();

        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(req.getPhoneNumber()), eq(BaseStatus.ACTIVE))).willReturn(Optional.of(UserFactory.createUser()));

        // then
        Assertions.assertThat(userService.checkPhoneNumberDuplication(req.getPhoneNumber())).isEqualTo(true);

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