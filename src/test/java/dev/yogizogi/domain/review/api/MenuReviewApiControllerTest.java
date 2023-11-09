package dev.yogizogi.domain.review.api;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.review.factory.dto.CreateMenuReviewFactory;
import dev.yogizogi.domain.review.model.dto.request.CreateMenuReviewInDto;
import dev.yogizogi.domain.review.service.MenuReviewService;
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
@WebMvcTest(value = {MenuReviewApiController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("MenuReviewApiController 동작 테스트")
class MenuReviewApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuReviewService menuReviewService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 메뉴_리뷰_생성() throws Exception {

        // given
        CreateMenuReviewInDto 요청 = CreateMenuReviewFactory.createMenuReviewInDto();

        // mocking
        given(menuReviewService.createMenuReview(
                eq(요청.getReviewId()), eq(요청.getMenuId()), eq(요청.getContent()), eq(요청.getRecommend()), eq(요청.getImageUrl())))
                .willReturn(CreateMenuReviewFactory.createMenuReviewOutDto());

        // when
        // then
        mockMvc.perform(
                        post("/api/menu-reviews/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(objectMapper.writeValueAsString(요청))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data.reviewId").value(요청.getReviewId().toString())
                )
                .andExpect(
                        jsonPath("$.data.menuId").value(요청.getMenuId())
                );

    }

}