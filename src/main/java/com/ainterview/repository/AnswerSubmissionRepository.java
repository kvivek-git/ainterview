package com.ainterview.repository;

import com.ainterview.model.AnswerSubmission;
import com.ainterview.model.InterviewSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerSubmissionRepository extends JpaRepository<AnswerSubmission, Long> {
    List<AnswerSubmission> findByInterviewSession(InterviewSession session);
}
