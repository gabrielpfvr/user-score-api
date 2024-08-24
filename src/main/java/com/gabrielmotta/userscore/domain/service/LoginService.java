package com.gabrielmotta.userscore.domain.service;

import com.gabrielmotta.userscore.api.dto.LoginRequest;
import com.gabrielmotta.userscore.api.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtService jwtService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        var user = this.userService.findByEmail(loginRequest.getUsername());

        if (!user.isLoginCorrect(loginRequest, this.passwordEncoder)) {
            throw new BadCredentialsException("Wrong username/password!");
        }

        var token = this.jwtService.generateToken(user);

        return LoginResponse.builder()
            .token(token)
            .expiresIn(jwtService.getExpiresIn())
            .build();
    }
}
