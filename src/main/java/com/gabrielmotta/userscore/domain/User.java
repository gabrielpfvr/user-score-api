package com.gabrielmotta.userscore.domain;

import com.gabrielmotta.userscore.api.dto.LoginRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public boolean isLoginCorrect(LoginRequest request, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(request.getPassword(), this.password);
    }
}
