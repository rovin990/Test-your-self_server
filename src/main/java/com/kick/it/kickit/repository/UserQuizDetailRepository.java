package com.kick.it.kickit.repository;

import com.kick.it.kickit.entities.UserQuizDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuizDetailRepository extends JpaRepository<UserQuizDetail,Long> {

    public List<UserQuizDetail> findByUsernameAndQuizIdAndAttemptNo(String username,Long quizId,int attemptNo);
    public List<UserQuizDetail> findAllByUsername(String username);

    public int countByQuizIdAndUsername(Long quizId,String username);
    public List<UserQuizDetail> findByQuizIdAndAttemptNo(Long quizId,int attemptNo);
}
