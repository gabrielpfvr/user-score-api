package com.gabrielmotta.userscore.domain.service;

import com.gabrielmotta.userscore.infra.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;

    public String generateToken(UserDetails user) {
        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
            .issuer("app")
            .subject(user.getUsername())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(this.jwtProperties.expires()))
            .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public long getExpiresIn() {
        return this.jwtProperties.expires();
    }
}
