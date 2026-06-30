package com.ainterview.service;

import com.ainterview.dto.InterviewSessionResponse;
import com.ainterview.exception.InterviewSessionException;
import com.ainterview.exception.ResourceNotFoundException;
import com.ainterview.model.AnswerSubmission;
import com.ainterview.model.InterviewSession;
import com.ainterview.model.InterviewStatus;
import com.ainterview.model.User;
import com.ainterview.repository.InterviewSessionRepository;
import com.ainterview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewSessionService {
    final private InterviewSessionRepository sessionRepository;
    final private UserRepository userRepository;

    public InterviewSessionResponse startInterview(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found. "));

        boolean hasActiveSession = sessionRepository.existsByUserAndStatusIn(user, List.of(InterviewStatus.CREATED, InterviewStatus.INPROGRESS));
        if(hasActiveSession){
            throw new RuntimeException("User already has an active interview session. ");
        }
        InterviewSession session = InterviewSession.builder()
                .user(user)
                .status(InterviewStatus.INPROGRESS)
                .startedAt(new Date())
                .build();

        session = sessionRepository.save(session);

        return mapToResponse(session);
    }

    public InterviewSessionResponse completeInterview(Long sessionId){
        InterviewSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("InterviewSession", sessionId));

        if(session.getStatus() == InterviewStatus.COMPLETED){
            throw new InterviewSessionException("Session" + sessionId + "is already completed");
        }

        session.setStatus(InterviewStatus.COMPLETED);
        session.setEndedAt(new Date());
        if(!session.getSubmissions().isEmpty()){
            int total = session.getSubmissions().stream()
                    .filter(s -> s.getScore() != null)
                    .mapToInt(AnswerSubmission::getScore)
                    .sum();
            int count = (int) session.getSubmissions().stream()
                    .filter(s -> s.getScore() != null)
                    .count();

            if(count > 0){
                session.setOverallScore(total / count);
            }
        }

        sessionRepository.save(session);
        return mapToResponse(session);
    }

    public List<InterviewSession> getUserSessions(User user){
        return sessionRepository.findByUserOrderByStartedAtDesc(user);
    }

    private InterviewSessionResponse mapToResponse(InterviewSession session){
        return InterviewSessionResponse.builder()
                .id(session.getId())
                .status(session.getStatus().name())
                .startedAt(session.getStartedAt())
                .endedAt(session.getEndedAt())
                .overallScore(session.getOverallScore())
                .build();
    }
}
