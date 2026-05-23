package com.ainterview.repository;
import com.ainterview.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByDifficulty(String difficulty);

    List<Question> findByTopic(String topic);

    List<Question> findByCompany(String company);
}
