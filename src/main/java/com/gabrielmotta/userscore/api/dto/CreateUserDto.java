package com.gabrielmotta.userscore.api.dto;

import lombok.Data;

@Data
public class CreateUserDto {

    private String email;
    private String password;
}
