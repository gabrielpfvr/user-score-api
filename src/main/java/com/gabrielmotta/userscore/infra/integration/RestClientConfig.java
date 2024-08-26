package com.gabrielmotta.userscore.infra.integration;

import com.gabrielmotta.userscore.infra.properties.ClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final ClientProperties clientProperties;

    @Bean
    public RestClient brasilApiRestClient() {
        return RestClient.builder()
            .baseUrl(this.clientProperties.brasilApiCep())
            .build();
    }
}
