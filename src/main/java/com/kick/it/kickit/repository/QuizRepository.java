package com.kick.it.kickit.repository;

import com.kick.it.kickit.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

    public List<Quiz> findByIsPublished(boolean b);

    public List<Quiz> findByCategoryAndIsPublished(String category ,boolean b);
}
