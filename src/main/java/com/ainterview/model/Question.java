package com.ainterview.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    private String difficulty;

    private String topic;

    private String company;

    @Column(columnDefinition = "TEXT")
    private String expectedApproach;

    @Column(columnDefinition = "TEXT")
    private String starterCode;
}
