package com.gabrielmotta.userscore.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;

import java.time.Instant;

public class TestHelper {

    @SneakyThrows
    public static byte[] convertRequestToJsonBytes(Object object) {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsBytes(object);
    }

    public static Jwt mockJwt(Instant expiresAt) {
        return new Jwt(
            "iAmAJwt",
            Instant.ofEpochMilli(12345L),
            expiresAt,
            JwsHeader.with(SignatureAlgorithm.RS256).build().getHeaders(),
            JwtClaimsSet.builder()
                .subject("admin@email.com")
                .expiresAt(expiresAt)
                .build().getClaims()
        );
    }
}
