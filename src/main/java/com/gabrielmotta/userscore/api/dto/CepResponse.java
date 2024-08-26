package com.gabrielmotta.userscore.api.dto;

public record CepResponse(String cep, String state, String city, String neighborhood, String street) {
}
