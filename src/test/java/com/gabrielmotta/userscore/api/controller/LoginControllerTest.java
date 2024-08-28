package com.gabrielmotta.userscore.api.controller;

import com.gabrielmotta.userscore.api.config.SecurityConfig;
import com.gabrielmotta.userscore.api.dto.LoginRequest;
import com.gabrielmotta.userscore.domain.service.JwtService;
import com.gabrielmotta.userscore.domain.service.LoginService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.gabrielmotta.userscore.helpers.TestHelper.convertRequestToJsonBytes;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class})
@MockBeans({
    @MockBean(LoginService.class),
    @MockBean(JwtService.class),
    @MockBean(UserDetailsService.class),
    @MockBean(AuthenticationProvider.class),
})
@WebMvcTest(LoginController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class LoginControllerTest {

    private static final String API_URL = "/auth";

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    void login_shouldReturnBadRequest_ifMissingRequestFields() {
        mvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertRequestToJsonBytes(LoginRequest.builder().build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[*].message", containsInAnyOrder(
                "The field username must not be blank",
                "The field password must not be blank"
            )));
    }

    @Test
    @SneakyThrows
    void login_shouldReturnOk_ifRequestFieldsPresent() {
        mvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertRequestToJsonBytes(LoginRequest.builder()
                    .username("test@email.com")
                    .password("12345")
                    .build())
                ))
            .andExpect(status().isOk());
    }
}