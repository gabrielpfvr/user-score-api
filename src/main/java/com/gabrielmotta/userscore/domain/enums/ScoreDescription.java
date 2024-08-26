package com.gabrielmotta.userscore.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScoreDescription {
    
    INSUFFICIENT(0, 200, "Insuficiente"),
    UNACCEPTABLE(201, 500, "Inaceitável"),
    ACCEPTABLE(501, 700, "Aceitável"),
    RECOMMENDED(701, 1000, "Recomendável");
    
    private final int minValue;
    private final int maxValue;
    private final String description;

    public static String getScoreDescription(int score) {
        for (var scoreDescription : ScoreDescription.values()) {
            if (scoreDescription.isInRange(score)) {
                return scoreDescription.getDescription();
            }
        }
        return "Fora da regra";
    }

    private boolean isInRange(int score) {
        return score >= this.minValue && score <= this.maxValue;
    }
}
