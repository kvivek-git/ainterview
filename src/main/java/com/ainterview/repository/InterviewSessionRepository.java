package com.ainterview.repository;

import com.ainterview.model.InterviewSession;
import com.ainterview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long> {
    List<InterviewSession> findByUserOrderByStartedAtDesc(User user);
}

