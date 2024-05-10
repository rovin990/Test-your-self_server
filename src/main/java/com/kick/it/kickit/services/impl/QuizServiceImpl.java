package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.entities.Quiz;
import com.kick.it.kickit.repository.QuizRepository;
import com.kick.it.kickit.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    QuizRepository quizRepository;



    @Override
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz getQuiz(Long quizId) {
        return quizRepository.findById(quizId).get();
    }

    @Override
    public List<Quiz> getQuizzes() {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal.getName().substring(0,11).equalsIgnoreCase("masterAdmin")){
            return quizRepository.findAll();
        }
        //System.out.println(quizRepository.findAll().stream().filter(quiz -> quiz.getCreatedBy().equalsIgnoreCase(principal.getName())).toList());
        return quizRepository.findAll().stream().filter(quiz -> quiz.getCreatedBy().equalsIgnoreCase(principal.getName())).toList();
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return quizRepository.saveAndFlush(quiz);
    }

    @Override
    public void deleteQuiz(Long quizId) {
        quizRepository.deleteById(quizId);
    }

    @Override
    public List<Quiz> getActiveQuizzesAdmin() {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal.getName().substring(0,11).equalsIgnoreCase("masterAdmin")){
            return quizRepository.findByIsPublished(true);
        }
        return quizRepository.findByIsPublished(true).stream().filter(quiz -> quiz.getCreatedBy().equalsIgnoreCase(principal.getName())).toList();
    }

    @Override
    public List<Quiz> getCategoryActiveQuizzesAdmin(String category) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal.getName().substring(0,11).equalsIgnoreCase("masterAdmin")){
            return quizRepository.findByCategoryAndIsPublished(category,true);
        }
        return quizRepository.findByCategoryAndIsPublished(category,true).stream().filter(quiz -> quiz.getCreatedBy().equalsIgnoreCase(principal.getName())).toList();
    }

    @Override
    public List<Quiz> getActiveQuizzesUser() {
        return quizRepository.findByIsPublished(true);
    }

    @Override
    public List<Quiz> getCategoryActiveQuizzesUser(String category) {
        return quizRepository.findByCategoryAndIsPublished(category,true);
    }


}
