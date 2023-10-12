package dev.yogizogi.domain.authorization.api;

import static dev.yogizogi.domain.authorization.factory.fixtures.VerificationFixtures.받은_인증코드;
import static dev.yogizogi.domain.authorization.factory.fixtures.VerificationFixtures.받은_핸드폰_번호;
import static dev.yogizogi.domain.authorization.factory.fixtures.VerificationFixtures.상태_메시지;
import static dev.yogizogi.domain.authorization.factory.fixtures.VerificationFixtures.인증코드;
import static dev.yogizogi.domain.authorization.factory.fixtures.VerificationFixtures.저장된_인증코드;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰_번호;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.yogizogi.domain.authorization.factory.dto.VerificationFactory;
import dev.yogizogi.domain.authorization.service.VerificationService;
import dev.yogizogi.global.common.status.MessageStatus;
import dev.yogizogi.global.common.status.VerificationStatus;
import dev.yogizogi.global.util.RedisUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(value = {VerificationApiController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("VerificationApiController 동작 테스트")
class VerificationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    VerificationService verificationService;

    @MockBean
    RedisUtils redisUtils;

    @Test
    void 인증번호_요청() throws Exception {

        // given
        String 받을_핸드폰_번호 = 핸드폰_번호;
        String 받은_핸드폰_번호 = 핸드폰_번호;

        // mocking
        given(verificationService.sendVerificationCodeForSignUp(eq(받은_핸드폰_번호)))
                .willReturn(VerificationFactory.sendVerificationCodeOutDto());

        // when
        // then
        mockMvc.perform(
                        get("/api/verification/code")
                                .param("phoneNumber", 받을_핸드폰_번호)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.data.status")
                                .value(MessageStatus.SUCCESS.getDescription())
                )
                .andExpect(
                        jsonPath("$.data.description").value(상태_메시지)
                );

    }

    @Test
    void 인증번호_확인() throws Exception {

        // given
        String 받을_핸드폰_번호 = 핸드폰_번호;
        String 받을_인증코드 = 인증코드;


        // mocking
        given(redisUtils.findByKey(eq(받은_핸드폰_번호)))
                .willReturn(저장된_인증코드);

        given(verificationService.checkVerificationCode(eq(받은_핸드폰_번호), eq(받은_인증코드)))
                .willReturn(VerificationFactory.pass());

        // when
        // then
        mockMvc.perform(
                        get("/api/verification/check")
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