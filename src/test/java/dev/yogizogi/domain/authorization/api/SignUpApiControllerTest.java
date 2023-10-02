package dev.yogizogi.domain.authorization.api;

import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.계정;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.닉네임;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.식별자;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.authorization.service.SignUpService;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.domain.user.factory.dto.CreateUserFactory;
import dev.yogizogi.domain.user.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateUserOutDto;
import dev.yogizogi.domain.user.service.UserService;
import dev.yogizogi.global.common.status.DuplicationStatus;
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
@WebMvcTest(value = {SignUpApiController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("SignUpApiController 동작 테스트")
class SignUpApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SignUpService signUpService;

    @MockBean
    UserService userService;

    @MockBean
    JwtService jwtService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 회원_가입() throws Exception {

        // given
        CreateUserInDto req = CreateUserFactory.createUserInDto();
        CreateUserOutDto res = CreateUserFactory.createUserOutDto();

        // mocking
        given(signUpService.signUp(any())).willReturn(res);

        // when
        // then
        mockMvc.perform(
                        post("/api/sign-up/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data.id").value(식별자.toString())
                )
                .andExpect(
                        jsonPath("$.data.accountName").value(계정)
                );

    }

    @Test
    void 계정_중복_존재() throws Exception {

        // given
        String accountName = 계정;

        // mocking
        given(userService.checkAccountNameDuplication(eq(accountName))).willReturn(true);

        // when
        // then
        mockMvc.perform(
                        get("/api/sign-up/check-duplication-account")
                                .param("accountName", accountName)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.data.status")
                                .value(DuplicationStatus.EXIST.getDescription())
                )
                .andExpect(
                        jsonPath("$.data.checked").value(accountName)
                );

    }

    @Test
    void 계정_중복_미존재() throws Exception {

        // given
        String accountName = 계정;

        // mocking
        given(userService.checkAccountNameDuplication(eq(accountName))).willReturn(false);

        // when
        // then
        mockMvc.perform(
                        get("/api/sign-up/check-duplication-account")
                                .param("accountName", accountName)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.data.status")
                                .value(DuplicationStatus.NOT_EXIST.getDescription())
                )
                .andExpect(
                        jsonPath("$.data.checked").value(accountName)
                );

    }

    @Test
    void 닉네임_중복_존재() throws Exception {

        // given
        String nickname = 닉네임;

        // mocking
        given(userService.checkNicknameDuplication(eq(nickname))).willReturn(true);

        // when
        // then
        mockMvc.perform(
                        get("/api/sign-up/check-duplication-nickname")
                                .param("nickname", nickname)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.data.status")
                                .value(DuplicationStatus.EXIST.getDescription())
                )
                .andExpect(
                        jsonPath("$.data.checked").value(nickname)
                );

    }

    @Test
    void 닉네임_중복_미존재() throws Exception {

        // given
        String nickname = 닉네임;

        // mocking
        given(userService.checkNicknameDuplication(eq(nickname))).willReturn(false);

        // when
        // then
        mockMvc.perform(
                        get("/api/sign-up/check-duplication-nickname")
                                .param("nickname", nickname)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.data.status")
                                .value(DuplicationStatus.NOT_EXIST.getDescription())
                )
                .andExpect(
                        jsonPath("$.data.checked").value(nickname)
                );

    }

}