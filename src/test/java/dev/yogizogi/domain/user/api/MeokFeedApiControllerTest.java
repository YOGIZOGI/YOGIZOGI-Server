package dev.yogizogi.domain.user.api;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.yogizogi.domain.review.factory.entity.MenuReviewVOFactory;
import dev.yogizogi.domain.user.factory.dto.RetrieveMeokFeedFactory;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.dto.response.RetrieveMeokFeedOutDto;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.service.MeokFeedService;
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
@WebMvcTest(value = {MeokFeedApiController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("MeokFeedApiController 동작 테스트")
class MeokFeedApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeokFeedService meokFeedService;
    
    @Test
    void 먹피드_조회() throws Exception {

        // given
        User 조회할_유저 = UserFactory.createUserWithProfile();
        RetrieveMeokFeedOutDto 응답 = RetrieveMeokFeedFactory.retrieveMeokFeedOutDto();

        // mocking
        given(meokFeedService.retrieveMeokFeed(eq(조회할_유저.getId())))
                .willReturn(응답);

        // when
        // then
        mockMvc.perform(
                        get("/api/users/{userId}/meok-feed", 조회할_유저.getId().toString())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data.userProfile.nickname").value(조회할_유저.getProfile().getNickname())
                )
                .andExpect(
                        jsonPath("$.data.menuReviews[0].content").value(MenuReviewVOFactory.creatMenuReviewVOs().get(0).getContent())
                );

    }

    @Test
    void 먹피드_조회_데이터_없음() throws Exception {

        // given
        User 조회할_유저 = UserFactory.createUserWithProfile();
        RetrieveMeokFeedOutDto 응답 = RetrieveMeokFeedFactory.retrieveMeokFeedOutDtoNoContent();

        // mocking
        given(meokFeedService.retrieveMeokFeed(eq(조회할_유저.getId())))
                .willReturn(응답);

        // when
        // then
        mockMvc.perform(
                        get("/api/users/{userId}/meok-feed", 조회할_유저.getId().toString())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.status").value("NO_CONTENT")
                );

    }

}