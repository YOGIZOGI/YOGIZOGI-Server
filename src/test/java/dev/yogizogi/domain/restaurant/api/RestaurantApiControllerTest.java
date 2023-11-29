package dev.yogizogi.domain.restaurant.api;

import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.상호명;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.음식점_종류;
import static dev.yogizogi.domain.review.factory.fixtures.YogiMoodFixtures.PAIRING_MEAL;
import static dev.yogizogi.domain.review.factory.fixtures.YogiMoodFixtures.WITH_LOVER;
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
import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.restaurant.factory.dto.CreateRestaurantFactory;
import dev.yogizogi.domain.restaurant.factory.dto.RetrieveRestaurantFactory;
import dev.yogizogi.domain.restaurant.factory.dto.RetrieveRestaurantsByYogiMoodFactory;
import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.dto.response.RetrieveRestaurantsByYogiMoodsOutDto;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.service.RestaurantService;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles("test")
@WebMvcTest(value = {RestaurantApiController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("RestaurantApiController 동작 테스트")
class RestaurantApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 음식점_생성() throws Exception {

        // given
        CreateRestaurantInDto 요청 = CreateRestaurantFactory.createRestaurantInDto();

        // mocking
        given(restaurantService
                .createRestaurant(
                        eq(요청.getName()),
                        eq(요청.getTel()),
                        eq(요청.getAddress()),
                        eq(요청.getImageUrl()),
                        eq(요청.getTypes())
                )
        ).willReturn(CreateRestaurantFactory.createRestaurantOutDto());

        // when
        // then
        ResultActions 결과 = mockMvc.perform(
                        post("/api/restaurants/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(objectMapper.writeValueAsString(요청))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        결과.andExpect(jsonPath("$.data.name").value(상호명));

        for (int i = 0; i < 요청.getTypes().size(); i++) {
            결과.andExpect(jsonPath("$.data.types[%d]", i).value(음식점_종류.get(i)));
        }

    }

    @Test
    void 특정_음식점_조회() throws Exception {

        // given
        Restaurant 조회할_음식점 = RestaurantFactory.createRestaurant();
        List<Menu> 조회할_음식점_메뉴 = MenuFactory.createMenus();

        // mocking
        ReflectionTestUtils.setField(조회할_음식점, "menus", MenuFactory.createMenus());

        given(restaurantService.retrieveRestaurant(eq(조회할_음식점.getId())))
                .willReturn(RetrieveRestaurantFactory.getRestaurantOutDto());

        // when
        // then
        ResultActions 결과 = mockMvc
                .perform(
                        get("/api/restaurants/{restaurantId}", 조회할_음식점.getId())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        결과.andExpect(jsonPath("$.data.restaurantDetails.name").value(조회할_음식점.getRestaurantDetails().getName()));

        for (int i = 0; i < 조회할_음식점_메뉴.size(); i++) {
            결과.andExpect(jsonPath("$.data.menus[%d].details.name", i).value(조회할_음식점_메뉴.get(i).getDetails().getName()));
        }

    }

    @Test
    void 특정_요기무드_포함_음식점_조회() throws Exception {

        // given
        String 요기무드_1 = WITH_LOVER;
        String 요기무드_2 = PAIRING_MEAL;
        List<String> 조회할_요기무드_문자열 = List.of(요기무드_1, 요기무드_2);
        List<Restaurant> 포함하는_음식점 = RestaurantFactory.createRestaurants();
        List<RetrieveRestaurantsByYogiMoodsOutDto> 응답 = RetrieveRestaurantsByYogiMoodFactory.retrieveRestaurantsByYogiMoodsOutDtos();

        // mocking
        given(restaurantService.retrieveRestaurantsByYogiMood(eq(조회할_요기무드_문자열)))
                .willReturn(응답);

        // when
        // then
        ResultActions 결과 = mockMvc
                .perform(
                        get("/api/restaurants")
                                .queryParam("yogiMoods", 요기무드_1, 요기무드_2)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        for (int i = 0; i < 포함하는_음식점.size(); i++) {
            결과.andExpect(jsonPath("$.data[%d].id", i).value(포함하는_음식점.get(i).getId().toString()));
        }

    }

    @Test
    void 특정_요기무드_포함_음식점_조회_데이터_없음() throws Exception {

        // given
        String 요기무드_1 = WITH_LOVER;
        String 요기무드_2 = PAIRING_MEAL;
        List<String> 조회할_요기무드_문자열 = List.of(요기무드_1, 요기무드_2);
        List<RetrieveRestaurantsByYogiMoodsOutDto> 응답
                = RetrieveRestaurantsByYogiMoodFactory.retrieveRestaurantsByYogiMoodsOutDtosNoContent();

        // mocking
        given(restaurantService.retrieveRestaurantsByYogiMood(eq(조회할_요기무드_문자열))).willReturn(응답);

        // when
        // then
        mockMvc
                .perform(
                        get("/api/restaurants")
                                .queryParam("yogiMoods", 요기무드_1, 요기무드_2)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NO_CONTENT"));


    }

}