package com.kick.it.kickit.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answerList;
    private String answer;
    private String questionType;
    @OneToOne(cascade = CascadeType.ALL)
    private Options options;
    private List<String> examTags;
    private String image;

    private String topic;
    private String subject;

    private List<Long> quizList;

    private LocalDate createdDate;
    private String createdBy;
    private LocalDate updatedDate;
    private String updatedBy;
}
