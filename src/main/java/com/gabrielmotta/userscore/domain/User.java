package com.gabrielmotta.userscore.domain;

import com.gabrielmotta.userscore.api.dto.CepResponse;
import com.gabrielmotta.userscore.api.dto.UserRequest;
import com.gabrielmotta.userscore.domain.enums.Role;
import com.gabrielmotta.userscore.domain.enums.ScoreDescription;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Builder
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "USERS")
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    private String neighborhood;

    private String streetAddress;

    private String phoneNumber;

    private Integer score;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime deactivatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.deactivatedAt == null;
    }

    public static User from(UserRequest dto, CepResponse cepResponse) {
        var user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setScore(dto.getScore());
        user.setRole(dto.getRole());
        user.copyCepData(cepResponse);

        return user;
    }

    public void update(UserRequest dto, CepResponse cepResponse) {
        if (cepResponse != null) {
            this.copyCepData(cepResponse);
        }
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.age = dto.getAge();
        this.phoneNumber = dto.getPhoneNumber();
        this.score = dto.getScore();
        this.role = dto.getRole();
    }

    private void copyCepData(CepResponse cepResponse) {
        this.zipCode = cepResponse.cep();
        this.state = cepResponse.state();
        this.city = cepResponse.city();
        this.neighborhood = cepResponse.neighborhood();
        this.streetAddress = cepResponse.street();
    }

    public String getScoreDescription() {
        return ScoreDescription.getScoreDescription(this.score);
    }

    public void deactivate() {
        this.deactivatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.deactivatedAt = null;
    }
}
