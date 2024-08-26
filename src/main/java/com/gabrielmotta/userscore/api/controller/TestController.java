package com.gabrielmotta.userscore.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("admin")
    public String testAdmin() {
        return "Hello, admin!";
    }

    @GetMapping("user")
    public String testUser() {
        return "Hello, user!";
    }
}
