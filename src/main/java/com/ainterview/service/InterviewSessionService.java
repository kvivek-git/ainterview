package com.ainterview.service;

import com.ainterview.dto.InterviewSessionResponse;
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
                .orElseThrow(() -> new RuntimeException("Session not found"));

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
