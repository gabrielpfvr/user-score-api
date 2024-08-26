package com.gabrielmotta.userscore.helpers;

import com.gabrielmotta.userscore.api.dto.UserRequest;
import com.gabrielmotta.userscore.domain.User;
import com.gabrielmotta.userscore.domain.enums.Role;

import java.time.LocalDateTime;

public class UserHelper {

    public static User mockAdminUser() {
        return User.builder()
            .id(1L)
            .name("Admin")
            .email("admin@email.com")
            .password("reallySecurePassword")
            .age(30)
            .zipCode("04794000")
            .state("SP")
            .city("São Paulo")
            .neighborhood("Vila Gertrudes")
            .streetAddress("Avenida das Nações Unidas")
            .phoneNumber("11988776655")
            .score(920)
            .role(Role.ADMIN)
            .createdAt(LocalDateTime.of(2024, 8, 26, 18, 50))
            .build();
    }

    public static UserRequest mockUserRequest() {
        return UserRequest.builder()
            .name("Jairo")
            .email("jairo@test.com")
            .password("belinha2014")
            .age(46)
            .cep("86010020")
            .phoneNumber("43988776644")
            .score(450)
            .role(Role.USER)
            .build();
    }
}
