package com.gabrielmotta.userscore.infra.repository;

import com.gabrielmotta.userscore.domain.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class UserExampleBuilder {

    private String name;
    private Integer age;
    private String zipCode;

    private UserExampleBuilder() {
    }

    public static UserExampleBuilder builder() {
        return new UserExampleBuilder();
    }

    public UserExampleBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserExampleBuilder age(Integer age) {
        this.age = age;
        return this;
    }

    public UserExampleBuilder zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public Example<User> build() {
        var user = User.builder()
            .name(this.name)
            .age(this.age)
            .zipCode(this.zipCode)
            .build();

        var matcher = ExampleMatcher.matchingAll()
            .withIgnoreNullValues()
            .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("age", ExampleMatcher.GenericPropertyMatchers.exact())
            .withMatcher("zipCode", ExampleMatcher.GenericPropertyMatchers.exact());

        return Example.of(user, matcher);
    }
}
