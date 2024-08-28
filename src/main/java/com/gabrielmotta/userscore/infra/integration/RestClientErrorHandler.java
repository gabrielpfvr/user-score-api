package com.gabrielmotta.userscore.infra.integration;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

public class RestClientErrorHandler implements RestClient.ResponseSpec.ErrorHandler {

    @Override
    public void handle(HttpRequest request, ClientHttpResponse response) throws IOException {
        throw new RestClientException(String.format("Error while performing client request: %s, status: %s",
            request.getURI(), response.getStatusCode()));
    }
}

