package com.kick.it.kickit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Responses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long questionId;
    private String answer;
    private String correctAnswer;
}
