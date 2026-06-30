package com.ainterview.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public record EvaluationResult (
    int score,
    String feedback,
    String timeComplexity,
    String spaceComplexity,
    String strengths,
    String improvements
) { }
