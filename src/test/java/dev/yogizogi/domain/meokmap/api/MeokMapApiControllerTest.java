package dev.yogizogi.domain.meokmap.api;

import static dev.yogizogi.global.common.model.constant.Format.DONE;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.meokmap.factory.dto.AddRestaurantOnMapFactory;
import dev.yogizogi.domain.meokmap.factory.dto.GetMeokMapFactory;
import dev.yogizogi.domain.meokmap.factory.dto.RemoveRestaurantOnMapFactory;
import dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapFixtures;
import dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapRestaurantFixtures;
import dev.yogizogi.domain.meokmap.model.dto.request.AddRestaurantOnMeokMapInDto;
import dev.yogizogi.domain.meokmap.model.dto.request.RemoveRestaurantOnMeokMapInDto;
import dev.yogizogi.domain.meokmap.model.dto.response.GetMeokMapOutDto;
import dev.yogizogi.domain.meokmap.service.MeokMapService;
import dev.yogizogi.domain.security.service.JwtService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
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
@WebMvcTest(value = {MeokMapApiController.class},
    excludeFilters =  {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
    }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("MeokMapApiController 동작 테스트")
class MeokMapApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeokMapService meokMapService;

    @MockBean
    private JwtService jwtService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 먹지도_음식점_추가() throws Exception {

        // given
        AddRestaurantOnMeokMapInDto 요청 = AddRestaurantOnMapFactory.addRestaurantOnMeokMapInDto();

        // mocking
        given(meokMapService.addRestaurantOnMeokMap(
                eq(요청.getUserId()), eq(요청.getRestaurantId())))
                .willReturn(AddRestaurantOnMapFactory.addRestaurantOnMeokMapOutDto());

        // when
        // then
        mockMvc.perform(
                        post("/api/meok-maps/add/restaurants")
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
                        jsonPath("$.data.restaurantName").value(MeokMapRestaurantFixtures.음식점.getRestaurantDetails().getName())
                );

    }

    @Test
    void 먹지도_음식점_삭제() throws Exception {

        // given
        RemoveRestaurantOnMeokMapInDto 요청 = RemoveRestaurantOnMapFactory.removeRestaurantOnMeokMapInDto();

        // mocking
        given(meokMapService.removeRestaurantFromMeokMap(
                eq(요청.getUserId()), eq(요청.getRestaurantId())))
                .willReturn(DONE);

        // when
        // then
        mockMvc.perform(
                        delete("/api/meok-maps/remove/restaurants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(objectMapper.writeValueAsString(요청))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data").value(DONE)
                );

    }

    @Test
    void 먹지도_조회() throws Exception {

        // given
        UUID 사용자 = MeokMapFixtures.사용자.getId();
        List<GetMeokMapOutDto> 응답 = GetMeokMapFactory.getMeokMapOutDtos();

        // mocking
        given(jwtService.getUserId())
                .willReturn(사용자);

        given(meokMapService.getMeokMap(eq(사용자)))
                .willReturn(응답);

        // when
        // then
        mockMvc.perform(
                        get("/api/meok-maps")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data[0].restaurantId").value(응답.get(0).getRestaurantId().toString())
                );

    }





}