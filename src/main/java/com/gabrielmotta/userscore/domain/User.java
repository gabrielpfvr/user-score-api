package com.gabrielmotta.userscore.domain;

import com.gabrielmotta.userscore.api.dto.LoginRequest;
import com.gabrielmotta.userscore.domain.enums.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@Table(name = "USERS")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Integer age;
    private String zipCode;
    private String state;
    private String city;
    private String neighbourhood;
    private String streetAddress;
    private String phoneNumber;
    private Integer score;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public boolean isLoginCorrect(LoginRequest request, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(request.getPassword(), this.password);
    }
}
