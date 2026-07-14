package com.ainterview.dto.stats;

import com.ainterview.model.InterviewStatus;

import java.util.Date;

public record SessionSummary (
    Long sessionId,
    Date startedAt,
    Date endedAt,
    InterviewStatus status,
    Double averageScore,
    Long submissionCount
){}
