package com.kick.it.kickit.entities;

import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "imagedata")
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @Lob
    @Column(name = "data",columnDefinition ="BLOB")
    private byte[] imageData;

    public ImageData() {
    }

    public ImageData( String name, String type, byte[] imageData) {

        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
