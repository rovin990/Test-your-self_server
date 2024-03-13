package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.entities.Question;
import com.kick.it.kickit.entities.Quiz;
import com.kick.it.kickit.entities.Responses;
import com.kick.it.kickit.entities.UserQuizDetail;
import com.kick.it.kickit.repository.UserQuizDetailRepository;
import com.kick.it.kickit.services.QuestionService;
import com.kick.it.kickit.services.QuizService;
import com.kick.it.kickit.services.UserQuizDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserQuizDetailServiceImpl implements UserQuizDetailService {
    @Autowired
    UserQuizDetailRepository userQuizDetailRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuizService quizService;



    @Override
    public UserQuizDetail createTestResponse(UserQuizDetail userQuizDetail){
        Authentication principal =SecurityContextHolder.getContext().getAuthentication();

        //System.out.println("Hello"+principal.getName());

        userQuizDetail.setCreatedBy(principal.getName());
        userQuizDetail.setUsername(principal.getName());
        userQuizDetail.setCreatedDate(LocalDate.now());
        getScoreWithCorrectAndWrongNoOfQuestion(userQuizDetail);



        System.out.println("Return value"+userQuizDetail);
        return userQuizDetailRepository.save(userQuizDetail);
//       return userQuizDetail;
    }

    @Override
    public UserQuizDetail getTestResponse(Long quizId) {
        Authentication principal =SecurityContextHolder.getContext().getAuthentication();
        return userQuizDetailRepository.findByUsernameAndQuizId(principal.getName(),quizId);
    }

    @Override
    public List<UserQuizDetail> getAllAttemptedQuizResponse() {
        Authentication principal =SecurityContextHolder.getContext().getAuthentication();
        return userQuizDetailRepository.findAllByUsername(principal.getName());
    }

    private void getScoreWithCorrectAndWrongNoOfQuestion(UserQuizDetail userQuizDetail){
        Quiz quiz = quizService.getQuiz(userQuizDetail.getQuizId());
        double score=0l;
        int correct=0;
        int wrong=0;
        double markOfEachQuestion= ((quiz.getMaxMark()/quiz.getNoOfQuestion()));
//        List<Responses> responses= new ArrayList<>();

        for (Responses response: userQuizDetail.getResponses()){
            Question local = questionService.getQuestionByQuestionId(response.getQuestionId());
            response.setCorrectAnswer(local.getAnswer());
            if(local.getAnswer().equalsIgnoreCase(response.getAnswer())){
                score+=markOfEachQuestion;
                correct++;
            }
            else{
                score=score-(markOfEachQuestion/4);
                wrong++;
            }
        }
        System.out.println("Current quiz"+quiz);
        userQuizDetail.setAttemptedQuestion(userQuizDetail.getResponses().size());
        userQuizDetail.setNotAttemptedQuestion((quiz.getNoOfQuestion()-userQuizDetail.getAttemptedQuestion()));
        userQuizDetail.setScore(score);
        userQuizDetail.setCorrectQuestion(correct);
        userQuizDetail.setWrongQuestion(wrong);

       int attemptNo= findAttemptNo(userQuizDetail.getQuizId(),userQuizDetail.getUsername());
       userQuizDetail.setAttemptNo((attemptNo+1));
    }

    private int findAttemptNo(Long quizId,String username){
       return userQuizDetailRepository.countByQuizIdAndUsername(quizId,username);
    }
}
