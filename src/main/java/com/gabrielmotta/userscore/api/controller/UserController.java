package com.gabrielmotta.userscore.api.controller;

import com.gabrielmotta.userscore.api.dto.UserRequest;
import com.gabrielmotta.userscore.api.dto.CustomPageRequest;
import com.gabrielmotta.userscore.api.dto.UserResponse;
import com.gabrielmotta.userscore.api.dto.UserFilter;
import com.gabrielmotta.userscore.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserRequest dto) {
        this.userService.save(dto);
    }

    @GetMapping
    public PagedModel<UserResponse> getAllPaged(UserFilter userFilter, CustomPageRequest pageable) {
        return this.userService.findAll(userFilter, pageable);
    }

    @GetMapping("{id}")
    public UserResponse getById(@PathVariable Long id) {
        return this.userService.getUserResponseById(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody UserRequest dto) {
        this.userService.update(id, dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.userService.softDelete(id);
    }

    @PutMapping("activate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateUser(@PathVariable Long id) {
        this.userService.activateUser(id);
    }

    @GetMapping("score/{id}")
    public String getUserScoreDescription(@PathVariable Long id) {
        return this.userService.getUserScoreDescription(id);
    }

}
