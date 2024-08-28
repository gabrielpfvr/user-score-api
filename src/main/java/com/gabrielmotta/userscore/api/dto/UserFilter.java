package com.gabrielmotta.userscore.api.dto;

import com.gabrielmotta.userscore.domain.User;
import com.gabrielmotta.userscore.infra.repository.UserExampleBuilder;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Example;

@Data
@Builder
public class UserFilter {

    @Parameter(example = "gerso")
    private String name;
    @Parameter(example = "32")
    private Integer age;
    @Parameter(example = "04794000")
    private String zipCode;

    public Example<User> toExample() {
        return UserExampleBuilder.builder()
            .name(name)
            .age(age)
            .zipCode(zipCode)
            .build();
    }
}
