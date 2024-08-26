package com.gabrielmotta.userscore.api.dto;

import com.gabrielmotta.userscore.domain.User;
import com.gabrielmotta.userscore.domain.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private Integer age;
    private String zipCode;
    private String state;
    private String city;
    private String neighborhood;
    private String streetAddress;
    private String phoneNumber;
    private Integer score;
    private String scoreDescription;
    private Role role;
    private boolean active;

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .age(user.getAge())
            .zipCode(user.getZipCode())
            .state(user.getState())
            .city(user.getCity())
            .neighborhood(user.getNeighborhood())
            .streetAddress(user.getStreetAddress())
            .phoneNumber(user.getPhoneNumber())
            .score(user.getScore())
            .scoreDescription(user.getScoreDescription())
            .role(user.getRole())
            .active(user.isAccountNonLocked())
            .build();
    }
}
