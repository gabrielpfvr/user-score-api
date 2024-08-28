package com.gabrielmotta.userscore.api.dto;

import com.gabrielmotta.userscore.domain.enums.Role;
import org.junit.jupiter.api.Test;

import static com.gabrielmotta.userscore.helpers.UserHelper.mockAdminUser;
import static org.assertj.core.api.Assertions.assertThat;

class UserResponseTest {

    @Test
    void from_shouldReturnUserResponse_whenCalled() {
        assertThat(UserResponse.from(mockAdminUser()))
            .extracting("id", "name", "age", "address.zipCode", "address.state", "address.city", "address.neighborhood", "" +
                    "address.street", "phoneNumber", "score", "scoreDescription", "role", "active")
            .containsExactly(
                1L,
                "Admin",
                30,
                "04794000",
                "SP",
                "São Paulo",
                "Vila Gertrudes",
                "Avenida das Nações Unidas",
                "11988776655",
                920,
                "Recomendável",
                Role.ADMIN,
                true
            );
    }
}