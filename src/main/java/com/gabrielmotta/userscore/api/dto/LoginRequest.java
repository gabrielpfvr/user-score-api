package com.gabrielmotta.userscore.api.dto;

import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Builder
public class LoginRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
