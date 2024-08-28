package com.gabrielmotta.userscore.api.controller;

import com.gabrielmotta.userscore.api.controller.contract.IUserController;
import com.gabrielmotta.userscore.api.dto.CustomPageRequest;
import com.gabrielmotta.userscore.api.dto.UserFilter;
import com.gabrielmotta.userscore.api.dto.UserRequest;
import com.gabrielmotta.userscore.api.dto.UserResponse;
import com.gabrielmotta.userscore.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController implements IUserController {

    private final UserService userService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserRequest dto) {
        this.userService.save(dto);
    }

    @Override
    @GetMapping
    public PagedModel<UserResponse> getAllPaged(UserFilter userFilter, CustomPageRequest pageable) {
        return this.userService.findAll(userFilter, pageable);
    }

    @Override
    @GetMapping("{id}")
    public UserResponse getById(@PathVariable Long id) {
        return this.userService.getUserResponseById(id);
    }

    @Override
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody UserRequest dto) {
        this.userService.update(id, dto);
    }

    @Override
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.userService.softDelete(id);
    }

    @Override
    @PutMapping("activate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateUser(@PathVariable Long id) {
        this.userService.activateUser(id);
    }

    @Override
    @GetMapping("score/{id}")
    public String getUserScoreDescription(@PathVariable Long id) {
        return this.userService.getUserScoreDescription(id);
    }
}
