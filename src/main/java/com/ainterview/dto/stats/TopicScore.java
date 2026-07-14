package com.ainterview.dto.stats;

public record TopicScore(
        String topic,
        Double averageScore,
        Long questionCount
) {
}
