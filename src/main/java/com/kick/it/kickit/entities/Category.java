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

    @Column(length = 1000)
    private String description;
    @Lob
    @Column(name = "image",columnDefinition ="BLOB")
    private byte[] image;
    private String color;


    @OneToMany(mappedBy = "category")
    private Set<Quiz> quizSet = new LinkedHashSet<>();
}



