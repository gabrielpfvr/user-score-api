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
    private AddressResponse address;
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
            .address(user.getAddress() != null ? AddressResponse.from(user.getAddress()) : null)
            .phoneNumber(user.getPhoneNumber())
            .score(user.getScoreValue())
            .scoreDescription(user.getScoreDescription())
            .role(user.getRole())
            .active(user.isAccountNonLocked())
            .build();
    }
}
