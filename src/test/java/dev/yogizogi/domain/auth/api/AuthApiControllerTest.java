package dev.yogizogi.domain.auth.api;

import static dev.yogizogi.domain.auth.factory.factory.LoginFactory.LoginOutDto;
import static dev.yogizogi.domain.auth.factory.fixtures.TokenFixtures.리프레쉬_토큰;
import static dev.yogizogi.domain.auth.factory.fixtures.TokenFixtures.어세스_토큰;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.계정;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.닉네임;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.식별자;
import static dev.yogizogi.domain.auth.factory.fixtures.VerificationFixture.*;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰번호;
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
import dev.yogizogi.domain.auth.factory.factory.VerificationFactory;
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
import dev.yogizogi.global.common.status.MessageStatus;
import dev.yogizogi.global.common.status.VerificationStatus;
import dev.yogizogi.global.util.RedisUtils;
import dev.yogizogi.infra.coolsms.CoolSmsService;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
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
    CoolSmsService coolSmsService;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RedisUtils redisUtils;

    @MockBean
    PasswordEncoder passwordEncoder;

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

    @Test
    void 인증번호_요청() throws Exception {

        // given
        String 받을_핸드폰_번호 = 핸드폰번호;
        String 받은_핸드폰_번호 = 핸드폰번호;

        // mocking
        given(authService.sendVerificationCode(eq(받은_핸드폰_번호)))
                .willReturn(VerificationFactory.sendVerificationCodeOutDto());

        // when
        // then
        mockMvc.perform(
                        get("/api/auth/send-verification-code")
                                .param("phoneNumber", 받을_핸드폰_번호)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.data.status")
                                .value(MessageStatus.SUCCESS.getDescription())
                )
                .andExpect(
                        jsonPath("$.data.message").value(상태_메시지)
                );

    }

    @Test
    void 인증번호_확인() throws Exception {

        // given
        String 받을_핸드폰_번호 = 핸드폰번호;
        String 받을_인증코드 = 인증코드;


        // mocking
        given(redisUtils.findByKey(eq(받은_핸드폰_번호)))
                .willReturn(저장된_인증코드);

        given(authService.checkVerificationCode(eq(받은_핸드폰_번호), eq(받은_인증코드)))
                .willReturn(VerificationFactory.pass());

        // when
        // then
        mockMvc.perform(
                        get("/api/auth/check-verification-code")
                                .param("phoneNumber", 받을_핸드폰_번호)
                                .param("code", 받을_인증코드)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.data.status")
                                .value(VerificationStatus.PASS.getDescription())
                )
                .andExpect(
                        jsonPath("$.data.phoneNumber").value(받은_핸드폰_번호)
                );

    }



}