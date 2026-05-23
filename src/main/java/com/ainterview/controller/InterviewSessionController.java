package com.ainterview.controller;

import com.ainterview.dto.InterviewSessionResponse;
import com.ainterview.dto.StartInterviewRequest;
import com.ainterview.service.InterviewSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewSessionController {
    private final InterviewSessionService sessionService;

    @PostMapping("/start")
    public InterviewSessionResponse startInterview(Authentication authentication){
        String email = authentication.getName();
        return sessionService.startInterview(email);
    }

    @PostMapping("/{sessionId}/complete")
    public InterviewSessionResponse completeInterview(@PathVariable Long sessionId){
        return sessionService.completeInterview(sessionId);
    }
}
