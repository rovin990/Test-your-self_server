package com.kick.it.kickit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQuizDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Long quizId;
    private String quizTitle;
    private int attemptNo;
    private int attemptedQuestion;
    private int notAttemptedQuestion;
    private int correctQuestion;
    private int wrongQuestion;
    private double score;
    private float timeTaken;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Responses> responses;

    private LocalDate createdDate;
    private String createdBy;
    private LocalDate updatedDate;
    private String updatedBy;
}
