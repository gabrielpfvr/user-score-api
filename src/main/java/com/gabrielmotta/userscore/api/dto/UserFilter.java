package com.gabrielmotta.userscore.api.dto;

import com.gabrielmotta.userscore.domain.User;
import com.gabrielmotta.userscore.infra.repository.UserExampleBuilder;
import lombok.Data;
import org.springframework.data.domain.Example;

@Data
public class UserFilter {

    private String name;
    private Integer age;
    private String zipCode;

    public Example<User> toExample() {
        return UserExampleBuilder.builder()
            .name(name)
            .age(age)
            .zipCode(zipCode)
            .build();
    }
}
