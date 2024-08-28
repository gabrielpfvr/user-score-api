package com.gabrielmotta.userscore.domain.enums;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreDescriptionTest {

    @Test
    void getScoreDescription_shouldMatchEnumValues() {
        var rangeInsufficient = IntStream.range(0, 200).boxed().toList();
        rangeInsufficient.forEach(score -> assertEquals("Insuficiente", ScoreDescription.getScoreDescription(score)));

        var rangeUnacceptable = IntStream.range(201, 500).boxed().toList();
        rangeUnacceptable.forEach(score -> assertEquals("Inaceitável", ScoreDescription.getScoreDescription(score)));

        var rangeAcceptable = IntStream.range(501, 700).boxed().toList();
        rangeAcceptable.forEach(score -> assertEquals("Aceitável", ScoreDescription.getScoreDescription(score)));

        var rangeRecommended = IntStream.range(701, 1000).boxed().toList();
        rangeRecommended.forEach(score -> assertEquals("Recomendável", ScoreDescription.getScoreDescription(score)));
    }
}
