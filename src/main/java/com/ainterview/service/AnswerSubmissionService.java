package com.ainterview.service;

import com.ainterview.dto.AnswerSubmissionResponse;
import com.ainterview.dto.EvaluationResult;
import com.ainterview.dto.SubmitAnswerRequest;
import com.ainterview.exception.InterviewSessionException;
import com.ainterview.exception.ResourceNotFoundException;
import com.ainterview.model.*;
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
    final private AiEvaluationService aiEvaluationService;

    public AnswerSubmissionResponse submitAnswer(SubmitAnswerRequest request){
        InterviewSession session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Session not found", request.getSessionId()));
        if(session.getStatus() != InterviewStatus.INPROGRESS){
            throw new InterviewSessionException("InterviewSession is not active");
        }
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found", request.getQuestionId()));

        AnswerSubmission submission = AnswerSubmission.builder()
                .interviewSession(session)
                .question(question)
                .language(ProgrammingLanguage.valueOf(request.getLanguage()))
                .code(request.getCode())
                .build();
        AnswerSubmission saved = submissionRepository.save(submission);

        EvaluationResult result = aiEvaluationService.evaluate(saved);

        saved.setScore(result.score());
        saved.setAiFeedback(result.feedback());
        saved.setTimeComplexity(result.timeComplexity());
        saved.setSpaceComplexity(result.spaceComplexity());
        saved.setStrengths(result.strengths());
        saved.setImprovements(result.improvements());

        submissionRepository.save(saved);

        return AnswerSubmissionResponse.builder()
                .id(saved.getId())
                .sessionId(session.getId())
                .questionId(question.getId())
                .language(saved.getLanguage().name())
                .submittedAt(saved.getSubmittedAt())
                .score(saved.getScore())
                .aiFeedback(saved.getAiFeedback())
                .timeComplexity(saved.getTimeComplexity())
                .spaceComplexity(saved.getSpaceComplexity())
                .strengths(saved.getStrengths())
                .improvements(saved.getImprovements())
                .build();
    }
}
