package dev.yogizogi.domain.review.api;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.menu.factory.entity.MenuFactory;
import dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures;
import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.review.factory.dto.CreateMenuReviewFactory;
import dev.yogizogi.domain.review.factory.dto.GetMenuReviewFactory;
import dev.yogizogi.domain.review.factory.dto.GetMenuReviewsFactory;
import dev.yogizogi.domain.review.factory.entity.MenuReviewFactory;
import dev.yogizogi.domain.review.model.dto.request.CreateMenuReviewInDto;
import dev.yogizogi.domain.review.model.entity.MenuReview;
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
                        post("/api/reviews/menu-reviews/create")
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

    @Test
    void 특정_메뉴에_대한_모든_리뷰_조회() throws Exception {

        // given
        Long 조회할_메뉴 = MenuFixtures.메뉴1_식별자;

        // mocking
        given(menuReviewService.getMenuReviews(eq(조회할_메뉴)))
                .willReturn(GetMenuReviewsFactory.createMenuReviewOutDto());

        // when
        // then
        mockMvc.perform(
                        get("/api/reviews/menu-reviews/menus/" + 조회할_메뉴)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data.menuId").value(조회할_메뉴)
                );

    }

    @Test
    void 특정_메뉴에_대한_모든_리뷰_조회_데이터_없음() throws Exception {

        // given
        Menu 조회할_메뉴 = MenuFactory.createMenu();

        // mocking
        given(menuReviewService.getMenuReviews(eq(조회할_메뉴.getId())))
                .willReturn(GetMenuReviewsFactory.createMenuReviewOutDtoNoContent());

        // when
        // then
        mockMvc.perform(
                        get("/api/reviews/menu-reviews/menus/{menuId}", 조회할_메뉴.getId())
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

    @Test
    void 메뉴_리뷰_단일_조회() throws Exception {

        // given
        MenuReview 조회할_메뉴_리뷰 = MenuReviewFactory.creatMenuReview();

        // mocking
        given(menuReviewService.getMenuReview(eq(조회할_메뉴_리뷰.getId())))
                .willReturn(GetMenuReviewFactory.getMenuReviewOutDto());

        // when
        // then
        mockMvc.perform(
                        get("/api/reviews/menu-reviews/{menuReviewId}", 조회할_메뉴_리뷰.getId())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data.menuReviewId").value(조회할_메뉴_리뷰.getId())
                );

    }

    @Test
    void 메뉴_리뷰_단일_조회_데이터_없음() throws Exception {

        // given
        MenuReview 조회할_메뉴_리뷰 = MenuReviewFactory.creatMenuReview();

        // mocking
        given(menuReviewService.getMenuReview(eq(조회할_메뉴_리뷰.getId())))
                .willReturn(GetMenuReviewFactory.getMenuReviewOutDtoNoContent());

        // when
        // then
        mockMvc.perform(
                        get("/api/reviews/menu-reviews/{menuReviewId}", 조회할_메뉴_리뷰.getId())
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