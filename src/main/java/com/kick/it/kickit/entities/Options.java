package com.kick.it.kickit.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;

    @OneToOne(cascade = CascadeType.ALL)
    private OptionsImage optionsImage;

}
