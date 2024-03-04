package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.entities.Quiz;
import com.kick.it.kickit.repository.QuizRepository;
import com.kick.it.kickit.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return quizRepository.findAll();
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
    public List<Quiz> getActiveQuizzes() {
        return quizRepository.findByIsPublished(true);
    }

    @Override
    public List<Quiz> getCategoryActiveQuizzes(String category) {
        return quizRepository.findByCategoryAndIsPublished(category,true);
    }
}
