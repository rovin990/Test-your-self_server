package com.kick.it.kickit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Lob
    @Column(name = "data",columnDefinition ="BLOB")
    private byte[] data;

    public QuestionImage(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }
}
