package dev.yogizogi.domain.user.service;

import static dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures.등록할_닉네임;
import static dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures.등록할_소개;
import static dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures.등록할_유저_식별자;
import static dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures.프로필_사진;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.닉네임;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰_번호;
import static dev.yogizogi.global.common.model.constant.Format.DONE;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.authorization.factory.dto.VerificationFactory;
import dev.yogizogi.domain.authorization.service.VerificationService;
import dev.yogizogi.domain.user.exception.AlreadyUsePasswordException;
import dev.yogizogi.domain.user.exception.NotExistUserException;
import dev.yogizogi.domain.user.exception.UserException;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.dto.response.CreateUserProfileOutDto;
import dev.yogizogi.domain.user.model.dto.response.DeleteUserOutDto;
import dev.yogizogi.domain.user.model.dto.response.FindPasswordOutDto;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.status.BaseStatus;
import dev.yogizogi.global.common.status.MessageStatus;
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
@DisplayName("UserService 비즈니스 로직 동작 테스트")
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private VerificationService verificationService;

    @Mock
    private CoolSmsService coolSmsService;


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void 닉네임_중복() throws UserException {

        // given

        // mocking
        given(userRepository.findByProfileNicknameAndStatus(eq(닉네임), eq(BaseStatus.ACTIVE))).willReturn(Optional.of(UserFactory.createUser()));

        //when

        // then
        Assertions.assertThat(userService.isUsedNickname(닉네임)).isEqualTo(true);

    }

    @Test
    void 핸드폰_번호_중복() throws UserException {

        // given

        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(핸드폰_번호), eq(BaseStatus.ACTIVE))).willReturn(Optional.of(UserFactory.createUser()));

        // then
        Assertions.assertThat(userService.isUsedPhoneNumber(핸드폰_번호)).isEqualTo(true);

    }

    @Test
    void 회원_탈퇴() {

        // given
        User removeUser = UserFactory.createUser();

        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(removeUser.getPhoneNumber()), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUser()));

        // when
        DeleteUserOutDto response = userService.deleteUser(removeUser.getPhoneNumber());

        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(BaseStatus.INACTIVE);

    }

    @Test
    void 회원_탈퇴_없는_계정() {

        // given


        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(핸드폰_번호), eq(BaseStatus.ACTIVE))).willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(() -> userService.deleteUser(핸드폰_번호))
                .isInstanceOf(NotExistUserException.class);

    }

    @Test
    void 비밀번호_찾기() {

        // given
        String 찾을_계정_핸드폰번호 = 핸드폰_번호;

        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(찾을_계정_핸드폰번호), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUser()));

        given(coolSmsService.sendOne(eq(찾을_계정_핸드폰번호)))
                .willReturn(VerificationFactory.successSingleMessageSentResponse());

        // when
        FindPasswordOutDto req = userService.findPassword(찾을_계정_핸드폰번호);

        // then
        Assertions.assertThat(req.getPhoneNumber()).isNotNull();
        Assertions.assertThat(req.getStatus()).isEqualTo(MessageStatus.SUCCESS);

    }

    @Test
    void 비밀번호_찾기_실패_존재하지_않는_계정() {

        // given
        String 찾을_계정_핸드폰번호 = 핸드폰_번호;

        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(찾을_계정_핸드폰번호), eq(BaseStatus.ACTIVE))).willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> userService.findPassword(찾을_계정_핸드폰번호))
                .isInstanceOf(NotExistUserException.class);

    }


    @Test
    void 비밀번호_변경() {

        // given
        String 변경할_계정 = 핸드폰_번호;
        String 변경할_비밀번호 = "update124!!";

        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(변경할_계정), eq(BaseStatus.ACTIVE))).willReturn(Optional.of(UserFactory.createUser()));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when
        String result = userService.updatePassword(변경할_계정, 변경할_비밀번호);

        // then
        Assertions.assertThat(result).isEqualTo(DONE);

    }

    @Test
    void 비밀번호_변경_실패_존재하지_않는_회원() {

        // given
        String 변경할_계정 = 핸드폰_번호;
        String 변경할_비밀번호 = "update124!!";


        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(변경할_계정), eq(BaseStatus.ACTIVE))).willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> userService.updatePassword(변경할_계정, 변경할_비밀번호))
                .isInstanceOf(NotExistUserException.class);

    }

    @Test
    void 비밀번호_변경_실패_이미_사용중인_비밀번호() {

        // given
        String 변경할_계정 = 핸드폰_번호;
        String 변경할_비밀번호 = "update124!!";

        // mocking
        given(userRepository.findByPhoneNumberAndStatus(eq(변경할_계정), eq(BaseStatus.ACTIVE))).willReturn(Optional.of(UserFactory.createUser()));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> userService.updatePassword(변경할_계정, 변경할_비밀번호))
                .isInstanceOf(AlreadyUsePasswordException.class);

    }

    @Test
    void 프로필_생성() {

        // given
        // mocking
        given(userRepository.findByIdAndStatus(eq(등록할_유저_식별자), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUser()));

        // when
        CreateUserProfileOutDto res = userService.createProfile(등록할_유저_식별자, 등록할_닉네임, 프로필_사진, 등록할_소개);

        // then
        Assertions.assertThat(res.getNickname()).isEqualTo(등록할_닉네임);
        Assertions.assertThat(res.getImageUrl()).isEqualTo(프로필_사진);
        Assertions.assertThat(res.getIntroduction()).isEqualTo(등록할_소개);

    }

    @Test
    void 프로필_생성_실패_존재하지_않는_회원() {

        // given
        User user = UserFactory.createUser();

        // mocking
        given(userRepository.findByIdAndStatus(eq(등록할_유저_식별자), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> userService.createProfile(등록할_유저_식별자, 등록할_닉네임, 프로필_사진, 등록할_소개))
                .isInstanceOf(NotExistUserException.class);

    }

}