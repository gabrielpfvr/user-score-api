package com.gabrielmotta.userscore.infra.integration;

import com.gabrielmotta.userscore.api.dto.CepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CepClient {

    private final RestClient restClient;

    public CepResponse getCepData(String cep) {
        return this.restClient.get()
            .uri("/{cep}", cep)
            .retrieve()
            .body(CepResponse.class);
    }
}
