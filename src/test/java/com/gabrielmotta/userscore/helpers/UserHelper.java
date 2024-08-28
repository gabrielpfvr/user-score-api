package com.gabrielmotta.userscore.helpers;

import com.gabrielmotta.userscore.api.dto.LoginRequest;
import com.gabrielmotta.userscore.api.dto.UserRequest;
import com.gabrielmotta.userscore.api.dto.UserResponse;
import com.gabrielmotta.userscore.domain.Address;
import com.gabrielmotta.userscore.domain.Score;
import com.gabrielmotta.userscore.domain.User;
import com.gabrielmotta.userscore.domain.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

public class UserHelper {

    public static User mockAdminUser() {
        return User.builder()
            .id(1L)
            .name("Admin")
            .email("admin@email.com")
            .password("reallySecurePassword")
            .age(30)
            .address(mockAddress())
            .phoneNumber("11988776655")
            .score(mockScore())
            .role(Role.ADMIN)
            .createdAt(LocalDateTime.of(2024, 8, 26, 18, 50))
            .build();
    }

    public static Address mockAddress() {
        return Address.builder()
            .zipCode("04794000")
            .state("SP")
            .city("São Paulo")
            .neighborhood("Vila Gertrudes")
            .street("Avenida das Nações Unidas")
            .build();
    }

    public static Score mockScore() {
        return Score.set(920);
    }

    public static UserResponse mockUserResponse() {
        return UserResponse.from(mockAdminUser());
    }

    public static Page<User> userResponsePage() {
        return new PageImpl<>(List.of(mockAdminUser()));
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

    public static LoginRequest mockLoginRequest() {
        return LoginRequest.builder()
            .username("user@email.com")
            .password("12345")
            .build();
    }
}
