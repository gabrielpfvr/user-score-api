package com.gabrielmotta.userscore.api.controller;

import com.gabrielmotta.userscore.api.config.SecurityConfig;
import com.gabrielmotta.userscore.api.dto.UserRequest;
import com.gabrielmotta.userscore.domain.service.JwtService;
import com.gabrielmotta.userscore.domain.service.UserService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.gabrielmotta.userscore.helpers.TestHelper.convertRequestToJsonBytes;
import static com.gabrielmotta.userscore.helpers.UserHelper.mockUserRequest;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class})
@MockBeans({
    @MockBean(UserService.class),
    @MockBean(JwtService.class),
    @MockBean(UserDetailsService.class),
    @MockBean(AuthenticationProvider.class),
})
@WebMvcTest(UserController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String API_URL = "/users";

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    void create_shouldReturnUnauthorized_ifNoTokenProvided() {
        mvc.perform(post(API_URL))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "USER")
    void create_shouldReturnForbidden_ifRoleUser() {
        mvc.perform(post(API_URL))
            .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ADMIN")
    void create_shouldReturnBadRequest_ifMissingRequestFields() {
        mvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertRequestToJsonBytes(UserRequest.builder().build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[*].message", containsInAnyOrder(
                "The field name must not be blank",
                "The field email must not be null",
                "The field password must not be blank",
                "The field age must not be null",
                "The field cep is invalid. It must contain exactly 8 digits.",
                "The field phoneNumber must not be blank",
                "The field score must not be null",
                "The field role must not be null"
            )));
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ADMIN")
    void create_shouldReturnCreated_ifRoleAdminAndRequestPresent() {
        mvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertRequestToJsonBytes(mockUserRequest())))
            .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void getAllPaged_shouldReturnUnauthorized_ifNoTokenProvided() {
        mvc.perform(get(API_URL))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void getAllPaged_shouldReturnForbidden_ifTokenWithNoAuthorities() {
        mvc.perform(get(API_URL))
            .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "USER")
    void getAllPaged_shouldReturnOk_ifValidUserToken() {
        mvc.perform(get(API_URL))
            .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ADMIN")
    void getAllPaged_shouldReturnOk_ifValidAdminToken() {
        mvc.perform(get(API_URL))
            .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void getById_shouldReturnUnauthorized_ifNoTokenProvided() {
        mvc.perform(get(API_URL + "/1"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void getById_shouldReturnForbidden_ifTokenWithNoAuthorities() {
        mvc.perform(get(API_URL + "/1"))
            .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "USER")
    void getById_shouldReturnOk_ifValidUserToken() {
        mvc.perform(get(API_URL + "/1"))
            .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ADMIN")
    void getById_shouldReturnOk_ifValidAdminToken() {
        mvc.perform(get(API_URL + "/1"))
            .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void update_shouldReturnUnauthorized_ifNoTokenProvided() {
        mvc.perform(put(API_URL + "/1"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "USER")
    void update_shouldReturnForbidden_ifUserToken() {
        mvc.perform(put(API_URL + "/1"))
            .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ADMIN")
    void update_shouldReturnForbidden_ifMissingRequestFields() {
        mvc.perform(put(API_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertRequestToJsonBytes(UserRequest.builder().build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[*].message", containsInAnyOrder(
                "The field name must not be blank",
                "The field email must not be null",
                "The field password must not be blank",
                "The field age must not be null",
                "The field cep is invalid. It must contain exactly 8 digits.",
                "The field phoneNumber must not be blank",
                "The field score must not be null",
                "The field role must not be null"
            )));
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ADMIN")
    void update_shouldReturnNoContent_ifRoleAdminAndRequestPresent() {
        mvc.perform(put(API_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertRequestToJsonBytes(mockUserRequest())))
            .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void delete_shouldReturnUnauthorized_ifNoTokenProvided() {
        mvc.perform(delete(API_URL + "/1"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "USER")
    void delete_shouldReturnForbidden_ifUserToken() {
        mvc.perform(delete(API_URL + "/1"))
            .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ADMIN")
    void delete_shouldReturnNoContent_ifAdminToken() {
        mvc.perform(delete(API_URL + "/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void activateUser_shouldReturnUnauthorized_ifNoTokenProvided() {
        mvc.perform(put(API_URL + "/activate/1"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "USER")
    void activateUser_shouldReturnForbidden_ifUserToken() {
        mvc.perform(put(API_URL + "/activate/1"))
            .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ADMIN")
    void activateUser_shouldReturnNoContent_ifAdminToken() {
        mvc.perform(put(API_URL + "/activate/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void getUserScoreDescription_shouldReturnUnauthorized_ifNoTokenProvided() {
        mvc.perform(get(API_URL + "/score/1"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void getUserScoreDescription_shouldReturnForbidden_ifTokenWithNoAuthorities() {
        mvc.perform(get(API_URL + "/score/1"))
            .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "USER")
    void getUserScoreDescription_shouldReturnOk_ifValidUserToken() {
        mvc.perform(get(API_URL + "/score/1"))
            .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ADMIN")
    void getUserScoreDescription_shouldReturnOk_ifValidAdminToken() {
        mvc.perform(get(API_URL + "/score/1"))
            .andExpect(status().isOk());
    }
}