package com.ainterview.repository;

import com.ainterview.model.AnswerSubmission;
import com.ainterview.model.InterviewSession;
import com.ainterview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerSubmissionRepository extends JpaRepository<AnswerSubmission, Long> {
    List<AnswerSubmission> findByInterviewSession(InterviewSession session);

    // AVG score for a specific user across all their submissions
    @Query("""
            SELECT AVG(a.score)
            FROM AnswerSubmission a
            WHERE a.interviewSession.user = :user
            AND a.score IS NOT NULL
            """)
    Double findAverageScoreByUser(@Param("user") User user);

    // MAX Score
    @Query("""
            SELECT MAX(a.score)
            FROM AnswerSubmission a
            WHERE a.interviewSession.user = :user
            AND a.score IS NOT NULL
            """)
    Integer findHighestScoreByUser(@Param("user") User user);

    // MIN Score
    @Query("""
            SELECT MIN(a.score)
            FROM AnswerSubmission a
            WHERE a.interviewSession.user = :user
            AND a.score IS NOT NULL
            """)
    Integer findLowestScoreByUser(@Param("user") User user);

    // GROUP BY topic — returns List<Object[]> where each row is [topic, avgScore, count]
    @Query("""
            SELECT a.question.topic, AVG(a.score), COUNT(a)
            FROM AnswerSubmission a
            WHERE a.interviewSession.user = :user
            AND a.score IS NOT NULL
            AND a.question.topic IS NOT NULL
            GROUP BY a.question.topic
            ORDER BY AVG(a.score) DESC
            """)
    List<Object[]> findScoreGroupedByTopic(@Param("user") User user);

    // AVG score per session
    @Query("""
            SELECT AVG(a.score)
            FROM AnswerSubmission a
            WHERE a.interviewSession = :session
            AND a.score IS NOT NULL
            """)
    Double findAverageScoreBySession(@Param("session") InterviewSession interviewSession);

    // Count submissions per session
    Long countByInterviewSession(InterviewSession session);
}
