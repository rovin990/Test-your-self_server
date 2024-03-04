package com.kick.it.kickit.repository;

import com.kick.it.kickit.entities.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageData,Long> {

    Optional<ImageData> findByName(String fileName);
}
