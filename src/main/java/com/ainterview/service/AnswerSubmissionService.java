package com.ainterview.service;

import com.ainterview.dto.AnswerSubmissionResponse;
import com.ainterview.dto.SubmitAnswerRequest;
import com.ainterview.model.AnswerSubmission;
import com.ainterview.model.InterviewSession;
import com.ainterview.model.ProgrammingLanguage;
import com.ainterview.model.Question;
import com.ainterview.repository.AnswerSubmissionRepository;
import com.ainterview.repository.InterviewSessionRepository;
import com.ainterview.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerSubmissionService {
    final private InterviewSessionRepository sessionRepository;
    final private QuestionRepository questionRepository;
    final private AnswerSubmissionRepository submissionRepository;

    public AnswerSubmissionResponse submitAnswer(SubmitAnswerRequest request){
        InterviewSession session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session not found"));
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        AnswerSubmission submission = AnswerSubmission.builder()
                .session(session)
                .question(question)
                .language(ProgrammingLanguage.valueOf(request.getLanguage()))
                .code(request.getCode())
                .score(80)
                .build();
        submission = submissionRepository.save(submission);

        return AnswerSubmissionResponse.builder()
                .id(submission.getId())
                .sessionId(session.getId())
                .questionId(question.getId())
                .language(submission.getLanguage().name())
                .submittedAt(submission.getSubmittedAt())
                .score(submission.getScore())
                .build();
    }
}
