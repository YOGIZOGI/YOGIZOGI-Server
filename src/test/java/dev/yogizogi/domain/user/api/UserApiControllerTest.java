package dev.yogizogi.domain.user.api;

import static dev.yogizogi.domain.user.factory.dto.DeleteUserFactory.deleteUserOutDto;
import static dev.yogizogi.domain.user.factory.fixtures.PasswordFixtures.변경할_비밀번호;
import static dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures.등록할_닉네임;
import static dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures.등록할_소개;
import static dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures.등록할_유저_식별자;
import static dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures.프로필_사진;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰_번호;
import static dev.yogizogi.global.common.model.constant.Format.DONE;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.domain.user.factory.dto.CreateProfileFactory;
import dev.yogizogi.domain.user.factory.dto.FindPasswordFactory;
import dev.yogizogi.domain.user.model.dto.request.CreateUserProfileInDto;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.domain.user.service.UserService;
import dev.yogizogi.global.common.status.MessageStatus;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(value = {UserApiController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("UserApiController 동작 테스트")
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 회원_탈퇴() throws Exception {

        // given
        String accountName = 핸드폰_번호;

        // mocking
        given(userService.deleteUser(eq(핸드폰_번호))).willReturn(deleteUserOutDto());

        // when
        // then
        mockMvc.perform(
                put("/api/users/delete")
                        .param("accountName", accountName)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data.accountName").value(핸드폰_번호)
                )
                .andExpect(
                        jsonPath("$.data.status").value("INACTIVE")
                );

    }

    @Test
    void 비멀번호_찾기() throws Exception {

        // given
        String 찾을_계정 = 핸드폰_번호;

        // mocking
        given(userService.findPassword(eq(찾을_계정))).willReturn(FindPasswordFactory.findPasswordOutDto());

        // when
        // then
        mockMvc.perform(
                        get("/api/users/find-password")
                                .param("phoneNumber", 찾을_계정)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data.status").value(MessageStatus.SUCCESS.getDescription())
                )
                .andExpect(
                        jsonPath("$.data.phoneNumber").value(찾을_계정)
                );

    }

    @Test
    void 비밀번호_수정() throws Exception {

        // given
        String 찾을_계정 = 핸드폰_번호;

        // mocking
        given(userService.updatePassword(eq(찾을_계정), eq(변경할_비밀번호))).willReturn(DONE);

        // when
        // then
        mockMvc.perform(
                        put("/api/users/update-password")
                                .param("phoneNumber", 찾을_계정)
                                .param("password", 변경할_비밀번호)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data").value(DONE)
                );


    }

    @Test
    void 프로필_생성() throws Exception {

        // given
        CreateUserProfileInDto req = CreateProfileFactory.createUserProfileInDto();

        // mocking
        given(jwtService.getUserId()).willReturn(등록할_유저_식별자);
        given(userService.createProfile(eq(등록할_유저_식별자), eq(req.getNickname()), eq(req.getImageUrl()), eq(req.getIntroduction())))
                .willReturn(CreateProfileFactory.createUserProfileOutDto());

        // when
        // then
        mockMvc.perform(
                        put("/api/users/create-profile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data.nickname").value(등록할_닉네임)
                )
                .andExpect(
                        jsonPath("$.data.imageUrl").value(프로필_사진)
                )
                .andExpect(
                        jsonPath("$.data.introduction").value(등록할_소개)
                );

    }

}