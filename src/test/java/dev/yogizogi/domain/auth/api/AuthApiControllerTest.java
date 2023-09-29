package dev.yogizogi.domain.auth.api;

import static dev.yogizogi.domain.auth.factory.factory.LoginFactory.LoginOutDto;
import static dev.yogizogi.domain.auth.factory.fixtures.TokenFixtures.리프레쉬_토큰;
import static dev.yogizogi.domain.auth.factory.fixtures.TokenFixtures.어세스_토큰;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.계정;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.닉네임;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.비밀번호;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.식별자;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.auth.factory.factory.LoginFactory;
import dev.yogizogi.domain.auth.model.dto.request.LoginInDto;
import dev.yogizogi.domain.auth.model.dto.response.LoginOutDto;
import dev.yogizogi.domain.auth.service.AuthService;
import dev.yogizogi.domain.security.service.CustomUserDetailsService;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.domain.user.api.UserApiController;
import dev.yogizogi.domain.user.factory.dto.CreateUserFactory;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateUserOutDto;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.domain.user.service.UserService;
import dev.yogizogi.global.common.status.BaseStatus;
import dev.yogizogi.global.common.status.DuplicationStatus;
import dev.yogizogi.global.util.RedisUtils;
import dev.yogizogi.infra.coolsms.CoolSmsService;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
@WebMvcTest(value = {AuthApiController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("AuthApiController 동작 테스트")
class AuthApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    JwtService jwtService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 로그인() throws Exception {

        // given
        LoginInDto req = LoginFactory.LoginInDto();
        LoginOutDto res = LoginFactory.LoginOutDto();

        // mocking
        given(authService.login(any())).willReturn(res);

        // when
        // then
        mockMvc.perform(
                        post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isOk())
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
                )
                .andExpect(
                        jsonPath("$.data.accessToken").value(어세스_토큰)
                )
                .andExpect(
                        jsonPath("$.data.refreshToken").value(리프레쉬_토큰)
                );

    }

    @Test
    void 회원_가입() throws Exception {

        // given
        CreateUserInDto req = CreateUserFactory.createUserInDto();
        CreateUserOutDto res = CreateUserFactory.createUserOutDto();

        // mocking
        given(userService.signUp(any())).willReturn(res);

        // when
        // then
        mockMvc.perform(
                        post("/api/auth/sign-up")
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
                        get("/api/auth/check-duplication-account")
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
                        get("/api/auth/check-duplication-account")
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
                        get("/api/auth/check-duplication-nickname")
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
                        get("/api/auth/check-duplication-nickname")
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