package com.kick.it.kickit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cId;
    private String title;
    private String description;
    private String image;
    private String color;


    @OneToMany(mappedBy = "category")
    private Set<Quiz> quizSet = new LinkedHashSet<>();
}



