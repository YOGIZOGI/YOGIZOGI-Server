package dev.yogizogi.domain.menu.api;

import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴1_음식명;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.menu.factory.dto.CreateMenuFactory;
import dev.yogizogi.domain.menu.model.dto.request.CreateMenuInDto;
import dev.yogizogi.domain.menu.service.MenuService;
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
@WebMvcTest(value = {MenuApiController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("MenuApiController 동작 테스트")
class MenuApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 메뉴_생성() throws Exception {

        // given
        CreateMenuInDto req = CreateMenuFactory.createMenuInDto();

        // mocking
        given(menuService.createMenu(
                eq(req.getRestaurantId()), eq(req.getName()), eq(req.getPrice()), eq(req.getDescription()), eq(req.getImageUrl())))
                .willReturn(CreateMenuFactory.createMenuOutDto());

        // when
        // then
        mockMvc.perform(
                        post("/api/menus/create")
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
                        jsonPath("$.data.name").value(메뉴1_음식명)
                );

    }

}