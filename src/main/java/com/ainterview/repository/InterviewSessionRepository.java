package com.ainterview.repository;

import com.ainterview.model.InterviewSession;
import com.ainterview.model.InterviewStatus;
import com.ainterview.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long> {
    List<InterviewSession> findByUserOrderByStartedAtDesc(User user);

    boolean existsByUserAndStatusIn(User user, List<InterviewStatus> statuses);

    @Query("""
            SELECT COUNT(s)
            FROM InterviewSession s
            WHERE s.user = :user
            """)
    Long countByUser(@Param("user") User user);

    @Query("""
            SELECT COUNT(s)
            FROM InterviewSession s
            WHERE s.user = :user
            AND s.status IN :status
            """)
    Long countByUserAndStatus(@Param("user") User user, @Param("status") InterviewStatus status);

    @Query("""
            SELECT s
            FROM InterviewSession s
            WHERE s.user = :user
            ORDER BY s.startedAt DESC
            """)
    List<InterviewSession> findRecentByUser(@Param("user") User user, Pageable pageable);
}

