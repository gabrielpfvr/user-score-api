package com.gabrielmotta.userscore.api.dto;

import com.gabrielmotta.userscore.infra.repository.UserExampleBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFilterTest {

    @Test
    void toExample_shouldReturnExampleOfUser_whenCalled() {
        var filter = UserFilter.builder().name("Jorjao").age(24).zipCode("86200000").build();
        var expected = UserExampleBuilder.builder().name("Jorjao").age(24).zipCode("86200000").build();
        var actual = filter.toExample();

        assertEquals(expected, actual);
    }

}