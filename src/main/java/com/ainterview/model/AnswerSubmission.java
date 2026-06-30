package com.ainterview.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "answer_submissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerSubmission {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private InterviewSession interviewSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Enumerated(EnumType.STRING)
    private ProgrammingLanguage language;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String code;

    private Date submittedAt;

    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String aiFeedback;

    private String timeComplexity;

    private String spaceComplexity;

    @Column(columnDefinition = "TEXT")
    private String strengths;

    @Column(columnDefinition = "TEXT")
    private String improvements;

    @PrePersist
    public void prePersist(){
        if(submittedAt == null)
            submittedAt = new Date();
    }

}
