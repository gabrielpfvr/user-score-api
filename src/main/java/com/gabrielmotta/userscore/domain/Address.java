package com.gabrielmotta.userscore.domain;

import com.gabrielmotta.userscore.api.dto.CepResponse;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@Builder
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Address {

    @Id
    private Long id;

    @Column(nullable = false, length = 8)
    private String zipCode;

    @Column(nullable = false, length = 2)
    private String state;

    @Column(nullable = false, length = 50)
    private String city;

    private String neighborhood;

    private String street;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Address from(CepResponse cepResponse) {
        var address = new Address();
        address.zipCode = cepResponse.cep();
        address.state = cepResponse.state();
        address.city = cepResponse.city();
        address.neighborhood = cepResponse.neighborhood();
        address.street = cepResponse.street();

        return address;
    }

    public static Address fromZipCode(String zipCode) {
        var address = new Address();
        address.zipCode = zipCode;

        return address;
    }

    public void update(CepResponse cepResponse) {
        this.zipCode = cepResponse.cep();
        this.state = cepResponse.state();
        this.city = cepResponse.city();
        this.neighborhood = cepResponse.neighborhood();
        this.street = cepResponse.street();
    }
}
