package com.ainterview.service;

import com.ainterview.dto.stats.SessionSummary;
import com.ainterview.dto.stats.TopicScore;
import com.ainterview.dto.stats.UserStatsResponse;
import com.ainterview.exception.ResourceNotFoundException;
import com.ainterview.model.InterviewStatus;
import com.ainterview.model.User;
import com.ainterview.repository.AnswerSubmissionRepository;
import com.ainterview.repository.InterviewSessionRepository;
import com.ainterview.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserStatsService {
    private final UserRepository userRepository;
    private final InterviewSessionRepository interviewSessionRepository;
    private final AnswerSubmissionRepository answerSubmissionRepository;

    public UserStatsResponse getStatsForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user", "email", email));

        // ------- aggregate queries
        Long totalSessions = interviewSessionRepository.countByUser(user);
        Long completedSessions = interviewSessionRepository.countByUserAndStatus(user, InterviewStatus.COMPLETED);
        Double averageScore = answerSubmissionRepository.findAverageScoreByUser(user);
        Integer highestScore = answerSubmissionRepository.findHighestScoreByUser(user);
        Integer lowestScore = answerSubmissionRepository.findLowestScoreByUser(user);

        // ------ topic breakdown
        List<TopicScore> scoreByTopic = answerSubmissionRepository
                .findScoreGroupedByTopic(user)
                .stream()
                .map(row -> new TopicScore(
                        (String) row[0], // topic name
                        row[1] != null ? Math.round((Double) row[1] * 10.0) / 10.0 : 0.0, // average score rounded to 1 decimal
                        (Long) row[2] // questionCount
                ))
                .toList();

        // ------ recent sessions
        List<SessionSummary> recentSessions = interviewSessionRepository
                .findRecentByUser(user, PageRequest.of(0, 5))
                .stream()
                .map(row -> new SessionSummary(
                        row.getId(),
                        row.getStartedAt(),
                        row.getEndedAt(),
                        row.getStatus(),
                        answerSubmissionRepository.findAverageScoreBySession(row),
                        answerSubmissionRepository.countByInterviewSession(row)
                ))
                .toList();

        return new UserStatsResponse(
                totalSessions,
                completedSessions,
                averageScore != null ? Math.round(averageScore * 10.0) / 10.0 : 0.0,
                highestScore != null ? highestScore : 0,
                lowestScore != null ? lowestScore : 0,
                scoreByTopic,
                recentSessions
        );
    }
}
