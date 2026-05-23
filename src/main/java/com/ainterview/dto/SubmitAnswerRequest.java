package com.ainterview.dto;

import lombok.Data;

@Data
public class SubmitAnswerRequest {
    private Long sessionId;
    private Long questionId;
    private String language;
    private String code;
}
