package com.gabrielmotta.userscore.api.dto;

import com.gabrielmotta.userscore.domain.Address;
import lombok.Data;

@Data
public class AddressResponse {

    private String zipCode;
    private String state;
    private String city;
    private String neighborhood;
    private String street;

    private AddressResponse() {
    }

    public static AddressResponse from(Address address) {
        var response = new AddressResponse();
        response.setZipCode(address.getZipCode());
        response.setState(address.getState());
        response.setCity(address.getCity());
        response.setNeighborhood(address.getNeighborhood());
        response.setStreet(address.getStreet());

        return response;
    }
}
