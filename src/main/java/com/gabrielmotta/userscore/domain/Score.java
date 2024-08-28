package com.gabrielmotta.userscore.domain;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Score {

    @Id
    private Long id;

    @Column(name = "score_value", nullable = false)
    private Integer value;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Score set(Integer value) {
        var score = new Score();
        score.setValue(value);

        return score;
    }

    public void update(Integer value) {
        this.value = value;
    }
}
