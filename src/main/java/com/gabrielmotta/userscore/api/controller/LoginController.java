package com.gabrielmotta.userscore.api.controller;

import com.gabrielmotta.userscore.api.controller.contract.ILoginController;
import com.gabrielmotta.userscore.api.dto.LoginRequest;
import com.gabrielmotta.userscore.api.dto.LoginResponse;
import com.gabrielmotta.userscore.domain.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController implements ILoginController {

    private final LoginService loginService;

    @Override
    @PostMapping
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return this.loginService.login(loginRequest);
    }
}
