package com.gabrielmotta.userscore.infra.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(long expires, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
