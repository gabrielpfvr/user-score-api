package com.gabrielmotta.userscore.domain.service;

import com.gabrielmotta.userscore.api.dto.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.gabrielmotta.userscore.helpers.UserHelper.mockAdminUser;
import static com.gabrielmotta.userscore.helpers.UserHelper.mockLoginRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @InjectMocks
    private LoginService service;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void login_shouldThrowBadCredentialsException_ifUserIsEmpty() {
        var loginRequest = mockLoginRequest();
        when(userService.findByEmail("user@email.com")).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadCredentialsException.class)
            .isThrownBy(() -> service.login(loginRequest))
            .withMessage("Wrong username/password!");

        verify(jwtService, never()).generateToken(any(UserDetails.class));
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void login_shouldThrowBadCredentialsException_ifPasswordIsIncorrect() {
        var loginRequest = mockLoginRequest();
        var mockUser = mockAdminUser();

        when(userService.findByEmail("user@email.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("12345", mockUser.getPassword())).thenReturn(false);

        assertThatExceptionOfType(BadCredentialsException.class)
            .isThrownBy(() -> service.login(loginRequest))
            .withMessage("Wrong username/password!");

        verify(jwtService, never()).generateToken(any(UserDetails.class));
    }

    @Test
    void login_shouldThrowAuthenticationServiceException_ifUserIsDeactivated() {
        var loginRequest = mockLoginRequest();
        var mockUser = mockAdminUser();
        mockUser.setDeactivatedAt(LocalDateTime.now());

        when(userService.findByEmail("user@email.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("12345", mockUser.getPassword())).thenReturn(true);

        assertThatExceptionOfType(AuthenticationServiceException.class)
            .isThrownBy(() -> service.login(loginRequest))
            .withMessage("Inactive user, please contact an administrator");

        verify(jwtService, never()).generateToken(any(UserDetails.class));
    }

    @Test
    void login_shouldReturnLoginResponse_ifUserAndCredentialsAreCorrect() {
        var loginRequest = mockLoginRequest();
        var mockUser = mockAdminUser();

        when(userService.findByEmail("user@email.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("12345", mockUser.getPassword())).thenReturn(true);
        when(jwtService.generateToken(mockUser)).thenReturn("iAmAJwt");
        when(jwtService.getExpiresIn()).thenReturn(36000L);

        assertThat(service.login(loginRequest))
            .isInstanceOf(LoginResponse.class)
            .extracting("token", "expiresIn")
            .containsExactly("iAmAJwt", 36000L);
    }
}