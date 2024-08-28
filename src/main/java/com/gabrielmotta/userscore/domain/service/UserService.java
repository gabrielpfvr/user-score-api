package com.gabrielmotta.userscore.domain.service;

import com.gabrielmotta.userscore.api.dto.CepResponse;
import com.gabrielmotta.userscore.api.dto.CustomPageRequest;
import com.gabrielmotta.userscore.api.dto.UserFilter;
import com.gabrielmotta.userscore.api.dto.UserRequest;
import com.gabrielmotta.userscore.api.dto.UserResponse;
import com.gabrielmotta.userscore.domain.User;
import com.gabrielmotta.userscore.domain.exception.UserScoreException;
import com.gabrielmotta.userscore.infra.integration.CepClient;
import com.gabrielmotta.userscore.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final CepClient cepClient;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    public void save(UserRequest dto) {
        this.validateExistingUser(dto.getEmail());
        var cepResponse = this.cepClient.getCepData(dto.getCep());
        var user = User.from(dto, cepResponse);
        this.setEncodedPassword(user, dto.getPassword());

        this.repository.save(user);
    }

    private void setEncodedPassword(User user, String password) {
        user.setPassword(this.passwordEncoder.encode(password));
    }

    private void validateExistingUser(String email) {
        if (this.findByEmail(email).isPresent()) {
            throw new UserScoreException("Email already registered!");
        }
    }

    public PagedModel<UserResponse> findAll(UserFilter userFilter, CustomPageRequest pageable) {
        var users = this.repository.findAll(userFilter.toExample(), pageable)
            .map(UserResponse::from);

        return new PagedModel<>(users);
    }

    public UserResponse getUserResponseById(Long id) {
        var user = this.findById(id);

        return UserResponse.from(user);
    }

    private User findById(Long id) {
        return this.repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    public void update(Long id, UserRequest dto) {
        var user = this.findById(id);
        if (!Objects.equals(user.getEmail(), dto.getEmail())) {
            this.validateExistingUser(dto.getEmail());
        }
        CepResponse cepResponse = null;

        if (!Objects.equals(dto.getCep(), user.getZipCode())) {
            cepResponse = this.cepClient.getCepData(dto.getCep());
        }
        this.setEncodedPassword(user, dto.getPassword());
        user.update(dto, cepResponse);

        this.repository.save(user);
    }

    public void softDelete(Long id) {
        var user = this.findById(id);
        user.deactivate();

        this.repository.save(user);
    }

    public void activateUser(Long id) {
        var user = this.findById(id);
        user.activate();

        this.repository.save(user);
    }

    public String getUserScoreDescription(Long id) {
        var user = this.findById(id);
        return String.join(" - ", user.getScoreValue().toString(), user.getScoreDescription());
    }
}
