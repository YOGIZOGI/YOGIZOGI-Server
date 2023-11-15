package dev.yogizogi.domain.restaurant.api;

import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴1_음식명;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴2_음식명;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.상호명;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.음식점_종류;
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
import dev.yogizogi.domain.restaurant.factory.dto.CreateRestaurantFactory;
import dev.yogizogi.domain.restaurant.factory.dto.GetRestaurantFactory;
import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.service.RestaurantService;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

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
        CreateRestaurantInDto req = CreateRestaurantFactory.createRestaurantInDto();

        // mocking
        given(restaurantService.createRestaurant(
                eq(req.getName()), eq(req.getTel()), eq(req.getAddress()), eq(req.getImageUrl()), eq(req.getTypes())))
                .willReturn(CreateRestaurantFactory.createRestaurantOutDto());

        // when
        // then
        mockMvc.perform(
                        post("/api/restaurants/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data.name").value(상호명)
                )
                .andExpect(
                        jsonPath("$.data.types[0]").value(음식점_종류.get(0))
                )
                .andExpect(
                        jsonPath("$.data.types[1]").value(음식점_종류.get(1))
                );

    }

    @Test
    void 특정_음식점_조회() throws Exception {

        // given
        Restaurant 조회할_음식점 = RestaurantFactory.createRestaurant();
        ReflectionTestUtils.setField(조회할_음식점, "menus", MenuFactory.createMenus());

        // mocking
        given(restaurantService.getRestaurant(eq(상호명))).willReturn(GetRestaurantFactory.getRestaurantOutDto());

        // when
        // then
        mockMvc.perform(
                        get("/api/restaurants")
                                .param("name", 상호명)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data.restaurantDetails.name").value(상호명)
                )
                .andExpect(
                        jsonPath("$.data.menus[0].menuDetails.name").value(메뉴1_음식명)
                )
                .andExpect(
                        jsonPath("$.data.menus[1].menuDetails.name").value(메뉴2_음식명)
                );

    }




}