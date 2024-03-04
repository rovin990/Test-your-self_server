package com.kick.it.kickit.repository;

import com.kick.it.kickit.entities.UserQuizDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuizDetailRepository extends JpaRepository<UserQuizDetail,Long> {

    public UserQuizDetail findByUsernameAndQuizId(String username,Long quizId);
    public List<UserQuizDetail> findAllByUsername(String username);
}
