package dev.yogizogi.domain.meokprofile.api;

import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.등록할_식별자;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.meokprofile.factory.dto.CreateMeokProfileFactory;
import dev.yogizogi.domain.meokprofile.model.dto.request.CreateMeokProfileInDto;
import dev.yogizogi.domain.meokprofile.service.MeokProfileService;
import dev.yogizogi.domain.security.service.JwtService;
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
@WebMvcTest(value = {MeokProfileApiController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("MeokProfileApiController 동작 테스트")
class MeokProfileApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeokProfileService meokProfileService;

    @MockBean
    private JwtService jwtService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 먹프로필_생성() throws Exception {

        // given
        CreateMeokProfileInDto req = CreateMeokProfileFactory.createMeokProfileInDto();

        // mocking
        given(jwtService.getUserId())
                .willReturn(등록할_식별자);

        given(meokProfileService.createMeokProfile(
                eq(등록할_식별자),
                        eq(req.getSpicyPreference()),
                        eq(req.getSpicyIntensity()),
                        eq(req.getSaltyPreference()),
                        eq(req.getSaltyIntensity()),
                        eq(req.getSweetnessPreference()),
                        eq(req.getSweetnessIntensity())))
                .willReturn(CreateMeokProfileFactory.createMeokProfileOutDto());

        // when
        // then
        mockMvc.perform(
                        post("/api/meok-profile/create")
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
                        jsonPath("$.data.preference.spicyPreference").value(req.getSpicyPreference())
                )
                .andExpect(
                        jsonPath("$.data.preference.saltyPreference").value(req.getSaltyPreference())
                )
                .andExpect(
                        jsonPath("$.data.preference.sweetnessPreference").value(req.getSweetnessPreference())
                )
                .andExpect(
                        jsonPath("$.data.intensity.spicyIntensity").value(req.getSpicyIntensity())
                )
                .andExpect(
                        jsonPath("$.data.intensity.saltyIntensity").value(req.getSaltyIntensity())
                )
                .andExpect(
                        jsonPath("$.data.intensity.sweetnessIntensity").value(req.getSweetnessIntensity())
                );

    }


}