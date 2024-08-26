package com.gabrielmotta.userscore.domain.service;

import com.gabrielmotta.userscore.api.dto.LoginRequest;
import com.gabrielmotta.userscore.api.dto.LoginResponse;
import com.gabrielmotta.userscore.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        var user = this.userService.findByEmail(loginRequest.getUsername());

        if (user.isEmpty() || !this.isPasswordCorrect(loginRequest, user.get())) {
            throw new BadCredentialsException("Wrong username/password!");
        }
        if (!user.get().isAccountNonLocked()) {
            throw new AuthenticationServiceException("Inactive user, please contact an administrator");
        }

        var token = this.jwtService.generateToken(user.get());

        return LoginResponse.builder()
            .token(token)
            .expiresIn(jwtService.getExpiresIn())
            .build();
    }

    public boolean isPasswordCorrect(LoginRequest request, User user) {
        return this.passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
