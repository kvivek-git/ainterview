package com.ainterview.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class InterviewSessionResponse {
    private Long id;
    private String status;
    private Date startedAt;
    private Date endedAt;
    private Integer overallScore;
}
