package com.ainterview.repository;
import com.ainterview.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Questions,Long> {
    List<Questions> findByDifficulty(String difficulty);

    List<Questions> findByTopic(String topic);

    List<Questions> findByCompany(String company);
}
