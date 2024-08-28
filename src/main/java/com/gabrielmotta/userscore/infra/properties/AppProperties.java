package com.gabrielmotta.userscore.infra.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(String name, String version) {
}
