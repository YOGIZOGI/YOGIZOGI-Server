package dev.yogizogi.domain.user.api;

import static dev.yogizogi.domain.user.factory.dto.DeleteUserFactory.deleteUserOutDto;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.계정;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.yogizogi.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = {UserApiController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void 회원_탈퇴() throws Exception {

        // given
        String accountName = 계정;

        // mocking
        given(userService.deleteUser(eq(계정))).willReturn(deleteUserOutDto());

        // when
        // then
        mockMvc.perform(
                put("/api/users/delete")
                        .param("accountName", accountName)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        jsonPath("$.data.accountName").value(계정)
                )
                .andExpect(
                        jsonPath("$.data.status").value("INACTIVE")
                );

    }

}