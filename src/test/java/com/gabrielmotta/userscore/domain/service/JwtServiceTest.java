package com.gabrielmotta.userscore.domain.service;

import com.gabrielmotta.userscore.infra.properties.JwtProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.gabrielmotta.userscore.helpers.TestHelper.mockJwt;
import static com.gabrielmotta.userscore.helpers.UserHelper.mockAdminUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService service;
    @Mock
    private JwtEncoder jwtEncoder;
    @Mock
    private JwtDecoder jwtDecoder;
    @Mock
    private JwtProperties jwtProperties;
    @Captor
    private ArgumentCaptor<JwtEncoderParameters> jwtEncoderParamsCaptor;

    @Test
    void generateToken_shouldReturnEncodedJwt_asString() {
        var mockUser = mockAdminUser();
        when(jwtEncoder.encode(any())).thenReturn(mockJwt(Instant.now().plusSeconds(20)));

        assertThat(service.generateToken(mockUser))
            .isInstanceOf(String.class)
            .isEqualTo("iAmAJwt");

        verify(jwtEncoder).encode(jwtEncoderParamsCaptor.capture());
        var claims = jwtEncoderParamsCaptor.getValue().getClaims();

        assertEquals("app", claims.getClaim("iss"));
        assertEquals("admin@email.com", claims.getClaim("sub"));
    }

    @Test
    void getExpiresIn_shouldReturnExpiresIn_fromJwtProperties() {
        when(jwtProperties.expires()).thenReturn(36000L);

        assertEquals(36000L, service.getExpiresIn());
    }

    @Test
    void extractUsername_shouldExtractUsername_fromJwtSubClaim() {
        when(jwtDecoder.decode(anyString())).thenReturn(mockJwt(Instant.now()));

        assertEquals("admin@email.com", service.extractUsername("iAmAJwt"));
    }

    @Test
    void extractExpirationDate_shouldExtractExpirationDate_fromJwtExpClaim() {
        when(jwtDecoder.decode(anyString())).thenReturn(mockJwt(Instant.now()));

        assertThat(service.extractExpirationDate("iAmAJwt"))
            .isCloseTo(Instant.now(), within(10, ChronoUnit.SECONDS));
    }

    @Test
    void isTokenValid_shouldReturnTrue_ifTokenIsNotExpiredAndUserIsNotDeactivated() {
        var mockJwt = mockJwt(Instant.now().plusSeconds(20));
        when(jwtDecoder.decode(anyString())).thenReturn(mockJwt);

        assertTrue(service.isTokenValid(mockJwt.getTokenValue(), mockAdminUser()));
    }

    @Test
    void isTokenValid_shouldReturnFalse_ifUserIsDeactivated() {
        var userDetails = mockAdminUser();
        userDetails.deactivate();
        var mockJwt = mockJwt(Instant.now().plusSeconds(20));
        when(jwtDecoder.decode(anyString())).thenReturn(mockJwt);

        assertFalse(service.isTokenValid(mockJwt.getTokenValue(), userDetails));
    }

    @Test
    void isTokenValid_shouldReturnFalse_ifTokenIsExpired() {
        var mockJwt = mockJwt(Instant.ofEpochMilli(26000));
        when(jwtDecoder.decode(anyString())).thenReturn(mockJwt);

        assertFalse(service.isTokenValid(mockJwt.getTokenValue(), mockAdminUser()));
    }
}