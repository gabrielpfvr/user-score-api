package com.gabrielmotta.userscore.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String token;
    private long expiresIn;
}
