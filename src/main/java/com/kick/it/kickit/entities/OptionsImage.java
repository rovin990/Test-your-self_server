package com.kick.it.kickit.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class OptionsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name = "option1",columnDefinition ="BLOB")
    private byte[] option1;
    @Lob
    @Column(name = "option2",columnDefinition ="BLOB")
    private byte[] option2;
    @Lob
    @Column(name = "option3",columnDefinition ="BLOB")
    private byte[] option3;
    @Lob
    @Column(name = "option4",columnDefinition ="BLOB")
    private byte[] option4;

    public OptionsImage() {
    }

    public OptionsImage(byte[] option1, byte[] option2, byte[] option3, byte[] option4) {
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }
}
