package com.kick.it.kickit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qId;
    private String title;
    private String description;
    private int maxMark;
    private int noOfQuestion;
    private boolean isPublished;
    private String category;


    private LocalDate createdDate;
    private String createdBy;
    private LocalDate updatedDate;
    private String updatedBy;
}
