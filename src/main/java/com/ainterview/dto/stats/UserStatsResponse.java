package com.ainterview.dto.stats;

import java.util.List;

public record UserStatsResponse (
        Long totalSessions,
        Long completedSessions,
        Double averageScore,
        Integer highestScore,
        Integer lowestScore,
        List<TopicScore> scoreByTopic,
        List<SessionSummary> recentSessions // last 5 sessions
) {
}
