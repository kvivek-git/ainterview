package com.ainterview.service;

import com.ainterview.dto.QuestionDto;
import com.ainterview.exception.ResourceNotFoundException;
import com.ainterview.model.Question;
import com.ainterview.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question createQuestion(QuestionDto dto) {

        Question question = Question.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .difficulty(dto.getDifficulty())
                .topic(dto.getTopic())
                .company(dto.getCompany())
                .expectedApproach(dto.getExpectedApproach())
                .starterCode(dto.getStarterCode())
                .build();

        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Question", id));
    }

    public Question updateQuestion(Long id, QuestionDto dto) {

        Question question = getQuestionById(id);

        question.setTitle(dto.getTitle());
        question.setDescription(dto.getDescription());
        question.setDifficulty(dto.getDifficulty());
        question.setTopic(dto.getTopic());
        question.setCompany(dto.getCompany());
        question.setExpectedApproach(dto.getExpectedApproach());
        question.setStarterCode(dto.getStarterCode());

        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {

        Question question = getQuestionById(id);

        questionRepository.delete(question);
    }
}
