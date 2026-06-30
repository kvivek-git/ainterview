package com.ainterview.service;

import com.ainterview.dto.EvaluationResult;
import com.ainterview.model.AnswerSubmission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiEvaluationService {
    Client client = new Client();

    String modelName = "gemini-3.5-flash";
    private final ObjectMapper objectMapper;

    public EvaluationResult evaluate(AnswerSubmission answerSubmission){
        // Implementation for evaluating the answer submission
        String prompt = buildPrompt(answerSubmission);
        try{
            GenerateContentResponse response = client.models.generateContent(
                    modelName,
                    prompt,
                    null
            );
            return parseResponse(Objects.requireNonNull(response.text()));
        } catch (Exception e) {
            log.error("Error during AI evaluation: {}", e.getMessage());
            return fallbackResult();
        }
    }

    private String buildPrompt(AnswerSubmission submission) {
        return """
            You are an expert technical interviewer evaluating a coding submission.
            
            ## Question
            Title: %s
            Description: %s
            
            ## Expected Approach
            %s
            
            ## Candidate's Submission
            Language: %s
            
            ```%s
            %s
            ```
            
            ## Your Task
            Evaluate this submission and respond ONLY with a JSON object in this exact format,
            no explanation before or after:
            
            {
              "score": <integer 0-100>,
              "feedback": "<2-3 sentence overall assessment>",
              "timeComplexity": "<Big-O time complexity of their solution>",
              "spaceComplexity": "<Big-O space complexity of their solution>",
              "strengths": "<what the candidate did well>",
              "improvements": "<specific things to improve>"
            }
            
            Scoring guide:
            - 90-100: Optimal solution, clean code, correct complexity
            - 70-89:  Correct solution with minor inefficiencies or style issues
            - 50-69:  Partially correct or suboptimal approach
            - 30-49:  Shows understanding but significant issues
            - 0-29:   Incorrect or missing solution
            """.formatted(
                submission.getQuestion().getTitle(),
                submission.getQuestion().getDescription(),
                submission.getQuestion().getExpectedApproach(),
                submission.getLanguage().name().toLowerCase(),
                submission.getLanguage().name().toLowerCase(),
                submission.getCode()
        );
    }

    private EvaluationResult fallbackResult() {
        return new EvaluationResult(
                0,
                "Evaluation unavailable at this time. Please try again later.",
                "N/A", "N/A", "N/A", "N/A"
        );
    }

    private EvaluationResult parseResponse(String rawText) throws Exception {
        // strip Markdown code fences if Claude wraps it in ```json ... ```
        String cleaned = rawText
                .replaceAll("```json\\s*", "")
                .replaceAll("```\\s*", "")
                .trim();

        return objectMapper.readValue(cleaned, EvaluationResult.class);
    }

}

