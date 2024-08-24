package com.gabrielmotta.userscore.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {

    private String message;
    private String field;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
