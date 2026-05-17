package com.ainterview.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "interview_sessions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewSession {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private InterviewStatus status = InterviewStatus.CREATED;

    private Date startedAt;

    private Date endedAt;

    private Integer overallScore;

    @OneToMany(mappedBy = "interviewSession", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AnswerSubmission> submissions = new ArrayList<>();

    @PrePersist
    public void prepersist(){
        if(startedAt == null){
            startedAt = new Date();
        }
        if(status == null){
            status = InterviewStatus.CREATED;
        }
    }


}
