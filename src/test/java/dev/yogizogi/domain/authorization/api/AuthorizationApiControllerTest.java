package dev.yogizogi.domain.authorization.api;

import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.리프레쉬_토큰;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.발행한_어세스_토큰;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.어세스_토큰;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.토큰에_포함된_식별자;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.토큰에_포함된_핸드포_번호;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.식별자;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰_번호;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.authorization.factory.dto.LoginFactory;
import dev.yogizogi.domain.authorization.model.dto.request.LoginInDto;
import dev.yogizogi.domain.authorization.model.dto.response.LoginOutDto;
import dev.yogizogi.domain.authorization.service.AuthorizationService;
import dev.yogizogi.domain.security.factory.dto.ReissueAccessTokenFactory;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.domain.user.service.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(value = {AuthorizationApiController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("AuthorizationApiController 동작 테스트")
class AuthorizationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthorizationService authorizationService;

    @MockBean
    UserService userService;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 로그인() throws Exception {

        // given
        LoginInDto req = LoginFactory.LoginInDto();
        LoginOutDto res = LoginFactory.LoginOutDto();

        // mocking
        given(authorizationService.login(req.getPhoneNumber(), req.getPassword())).willReturn(res);

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
                        jsonPath("$.data.firstLogin").isNotEmpty()
                )
                .andExpect(
                        jsonPath("$.data.accessToken").value(어세스_토큰)
                )
                .andExpect(
                        jsonPath("$.data.refreshToken").value(리프레쉬_토큰)
                );

    }

    @Test
    void 어세스_토큰_재발급() throws Exception {

        // given
        // mocking
        given(jwtService.reissueAccessToken(식별자, 핸드폰_번호))
                .willReturn(ReissueAccessTokenFactory.reissueAccessTokenOutDto());

        // when
        // then
        mockMvc.perform(
                        get("/api/auth/reissue-access-token")
                                .param("id", String.valueOf(토큰에_포함된_식별자))
                                .param("phoneNumber", 토큰에_포함된_핸드포_번호)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.data.id")
                                .value(String.valueOf(토큰에_포함된_식별자))
                )
                .andExpect(
                        jsonPath("$.data.accessToken")
                                .value(발행한_어세스_토큰)
                );

    }



}