package com.gabrielmotta.userscore.domain.service;

import com.gabrielmotta.userscore.domain.User;
import com.gabrielmotta.userscore.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return this.repository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

//    public User create(CreateUserDto dto) {
//        var user = new User(dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
//
//        return this.repository.save(user);
//    }
}
