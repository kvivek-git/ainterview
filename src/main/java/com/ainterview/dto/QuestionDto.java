package com.ainterview.dto;

import lombok.Data;

@Data
public class QuestionDto {
    private String title;

    private String description;

    private String difficulty;

    private String topic;

    private String company;

    private String expectedApproach;

    private String starterCode;
}
