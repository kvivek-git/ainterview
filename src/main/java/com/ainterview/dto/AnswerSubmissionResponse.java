package com.ainterview.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AnswerSubmissionResponse {
    private Long id;
    private Long sessionId;
    private Long questionId;
    private Date submittedAt;
    private String language;
    private Integer score;
}
