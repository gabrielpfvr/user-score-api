package com.gabrielmotta.userscore.domain.service;

import com.gabrielmotta.userscore.infra.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
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

    public String extractUsername(String token) {
        return (String) this.extractClaim(token, JwtClaimNames.SUB);
    }

    private Object extractClaim(String token, String claim) {
        var jwt = this.jwtDecoder.decode(token);
        return jwt.getClaims().get(claim);
    }

    public Instant extractExpirationDate(String token) {
        return (Instant) this.extractClaim(token, JwtClaimNames.EXP);
    }

    private boolean isTokenExpired(String token) {
        var expiration = extractExpirationDate(token);
        return expiration.isBefore(Instant.now());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        var username = this.extractUsername(token);
        if (!userDetails.isAccountNonLocked()) {
            return false;
        }
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
