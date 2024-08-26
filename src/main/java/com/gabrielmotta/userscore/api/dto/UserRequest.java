package com.gabrielmotta.userscore.api.dto;

import com.gabrielmotta.userscore.api.annotation.Cep;
import com.gabrielmotta.userscore.domain.enums.Role;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class UserRequest {

    @NotBlank
    private String name;

    @Email
    @NotNull
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Integer age;

    @Cep
    private String cep;

    @NotBlank
    private String phoneNumber;

    @Min(0)
    @Max(1000)
    @NotNull
    private Integer score;

    @NotNull
    private Role role;
}
