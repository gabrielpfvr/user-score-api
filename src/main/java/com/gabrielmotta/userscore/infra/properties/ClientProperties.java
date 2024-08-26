package com.gabrielmotta.userscore.infra.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client")
public record ClientProperties(String brasilApiCep) {
}
