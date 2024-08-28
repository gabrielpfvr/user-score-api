package com.gabrielmotta.userscore.domain;

import com.gabrielmotta.userscore.domain.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static com.gabrielmotta.userscore.helpers.CepHelper.mockCepResponse;
import static com.gabrielmotta.userscore.helpers.UserHelper.mockAdminUser;
import static com.gabrielmotta.userscore.helpers.UserHelper.mockUserRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User mockedUser;

    @BeforeEach
    void setup() {
        mockedUser = mockAdminUser();
    }

    @Test
    void getAuthorities_shouldReturnRole_asSingletonListOfGrantedAuthority() {
        assertEquals(Collections.singletonList(new SimpleGrantedAuthority("ADMIN")), mockedUser.getAuthorities());
    }

    @Test
    void getUsername_shouldReturnEmail() {
        assertEquals("admin@email.com", mockedUser.getUsername());
    }

    @Test
    void isAccountNonLocked_shouldReturnTrue_ifUserIsActive() {
        assertTrue(mockedUser.isAccountNonLocked());
    }

    @Test
    void isAccountNonLocked_shouldReturnFalse_ifUserIsDeactivated() {
        mockedUser.setDeactivatedAt(LocalDateTime.now());
        assertFalse(mockedUser.isAccountNonLocked());
    }

    @Test
    void from_shouldReturnUser_byReceivingUserRequestAndCepResponse() {
        var user = User.from(mockUserRequest(), mockCepResponse());

        assertThat(user)
            .isInstanceOf(User.class)
            .extracting("id", "name", "email", "password", "age", "address.zipCode", "address.state", "address.city",
                "address.neighborhood", "address.street", "phoneNumber", "score.value", "role", "createdAt", "deactivatedAt")
            .containsExactly(null, "Jairo", "jairo@test.com", null, 46, "86015010", "PR", "Londrina", "Jardim Higienópolis",
                "Avenida Higienópolis", "43988776644", 450, Role.USER, null, null);
    }

    @Test
    void update_shouldCopyRequestDataAndNotCep_whenCepNull() {
        mockedUser.update(mockUserRequest(), null);

        assertThat(mockedUser)
            .isInstanceOf(User.class)
            .extracting("id", "name", "email", "password", "age", "address.zipCode", "address.state", "address.city",
                "address.neighborhood", "address.street", "phoneNumber", "score.value", "role", "createdAt", "deactivatedAt")
            .containsExactly(1L, "Jairo", "jairo@test.com", "reallySecurePassword", 46, "04794000", "SP", "São Paulo",
                "Vila Gertrudes", "Avenida das Nações Unidas", "43988776644", 450, Role.USER,
                LocalDateTime.of(2024, 8, 26, 18, 50), null);
    }

    @Test
    void update_shouldCopyRequestDataAndCep_whenCepIsNotNull() {
        mockedUser.update(mockUserRequest(), mockCepResponse());

        assertThat(mockedUser)
            .isInstanceOf(User.class)
            .extracting("id", "name", "email", "password", "age", "address.zipCode", "address.state", "address.city",
                "address.neighborhood", "address.street", "phoneNumber", "score.value", "role", "createdAt", "deactivatedAt")
            .containsExactly(1L, "Jairo", "jairo@test.com", "reallySecurePassword", 46, "86015010", "PR", "Londrina",
                "Jardim Higienópolis", "Avenida Higienópolis", "43988776644", 450, Role.USER,
                LocalDateTime.of(2024, 8, 26, 18, 50), null);
    }

    @Test
    void getScoreDescription_shouldReturnScoreDescriptionString_basedOnScoreValue() {
        assertEquals("Recomendável", mockedUser.getScoreDescription());
    }

    @Test
    void deactivate_shouldSetTheDateAsNow_whenCalled() {
        mockedUser.deactivate();

        assertThat(mockedUser.getDeactivatedAt())
            .isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.MINUTES));
    }

    @Test
    void activate_shouldSetDeactivatedAdAsNull_whenCalled() {
        mockedUser.setDeactivatedAt(LocalDateTime.now());
        mockedUser.activate();

        assertNull(mockedUser.getDeactivatedAt());
    }

    @Test
    void getScoreValue_shouldReturnNull_ifScoreIsNull(){
        var user = User.builder().build();

        assertNull(user.getScoreValue());
    }

    @Test
    void getScoreValue_shouldReturnScoreValue_ifScoreIsNotNull(){
        assertEquals(920, mockedUser.getScoreValue());
    }

    @Test
    void getZipCode_shouldReturnNull_ifAddressIsNull(){
        var user = User.builder().build();

        assertNull(user.getZipCode());
    }

    @Test
    void getZipCode_shouldReturnScoreValue_ifAddressIsNotNull(){
        assertEquals("04794000", mockedUser.getZipCode());
    }
}