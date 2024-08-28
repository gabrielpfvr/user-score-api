package com.gabrielmotta.userscore.infra.integration;

import com.gabrielmotta.userscore.api.dto.CepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class CepClient {

    private final RestClient restClient;

    public CepResponse getCepData(String cep) {
        return this.restClient.get()
            .uri("/{cep}", cep)
            .retrieve()
            .onStatus(this.statusCodePredicate(), new RestClientErrorHandler())
            .body(CepResponse.class);
    }

    private Predicate<HttpStatusCode> statusCodePredicate() {
        return httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError();
    }
}
