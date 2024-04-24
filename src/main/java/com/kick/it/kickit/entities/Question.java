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
    @Column(length = 1000)
    private String title;

    @Column(length = 3000)
    private String code;

//    @Transient
//    private byte[] questionImgData;
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Answer> answerList;
    @Column(length = 1000)
    private String explanation;
    @Column(length = 1000)
    private String answer;
    private String questionType;

    private boolean isImageOps;

    private String examTags;

    @OneToOne(cascade = CascadeType.ALL)
    private Options options;
    @OneToOne(cascade = CascadeType.ALL)
    private QuestionImage questionImage;

    private String topic;
    private String subject;

    private String quizIds;

    private LocalDate createdDate;
    private String createdBy;
    private LocalDate updatedDate;
    private String updatedBy;
}
