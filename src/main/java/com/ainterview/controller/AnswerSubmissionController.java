package com.ainterview.controller;

import com.ainterview.dto.AnswerSubmissionResponse;
import com.ainterview.dto.SubmitAnswerRequest;
import com.ainterview.service.AnswerSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/submissions")
public class AnswerSubmissionController {
    private final AnswerSubmissionService answerSubmissionService;

    @PostMapping
    public AnswerSubmissionResponse submitAnswer(@RequestBody SubmitAnswerRequest request){
        return answerSubmissionService.submitAnswer(request);
    }
}
