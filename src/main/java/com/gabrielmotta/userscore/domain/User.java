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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 320)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 3)
    private Integer age;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Address address;

    @Column(nullable = false, length = 12)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Score score;

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
        user.setRole(dto.getRole());
        user.setScore(dto.getScore());
        user.copyCepData(cepResponse);

        return user;
    }

    public void update(UserRequest dto, CepResponse cepResponse) {
        if (cepResponse != null) {
            this.copyCepData(cepResponse);
        }
        this.setScore(dto.getScore());
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.age = dto.getAge();
        this.phoneNumber = dto.getPhoneNumber();
        this.role = dto.getRole();
    }

    private void copyCepData(CepResponse cepResponse) {
        if (this.address == null) {
            this.address = Address.from(cepResponse);
            this.address.setUser(this);
        } else {
            this.address.update(cepResponse);
        }
    }

    private void setScore(Integer score) {
        if (this.score == null) {
            this.score = Score.set(score);
            this.score.setUser(this);
        } else {
            this.score.update(score);
        }
    }

    public String getScoreDescription() {
        return this.score != null
            ? ScoreDescription.getScoreDescription(this.score.getValue())
            : null;
    }

    public Integer getScoreValue() {
        return this.score != null
            ? this.score.getValue()
            : null;
    }

    public String getZipCode() {
        return this.address != null
            ? this.address.getZipCode()
            : null;
    }

    public void deactivate() {
        this.deactivatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.deactivatedAt = null;
    }
}
