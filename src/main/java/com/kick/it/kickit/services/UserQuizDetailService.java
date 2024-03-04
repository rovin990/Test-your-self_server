package com.kick.it.kickit.services;

import com.kick.it.kickit.entities.UserQuizDetail;
import com.kick.it.kickit.repository.UserQuizDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserQuizDetailService {


    public UserQuizDetail createTestResponse(UserQuizDetail userQuizDetail);
    public UserQuizDetail getTestResponse(Long quizId);
    public List<UserQuizDetail> getAllTestResponse();
}
