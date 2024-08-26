package com.gabrielmotta.userscore.domain.service;

import com.gabrielmotta.userscore.domain.User;
import com.gabrielmotta.userscore.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

//    public User create(CreateUserDto dto) {
//        var user = new User(dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
//
//        return this.repository.save(user);
//    }
}
