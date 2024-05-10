package com.kick.it.kickit.services;

import com.kick.it.kickit.entities.Quiz;

import java.util.List;

public interface QuizService {

    public Quiz saveQuiz(Quiz quiz);

    public  Quiz getQuiz(Long quizId);

    public List<Quiz> getQuizzes();

    public Quiz updateQuiz(Quiz quiz);

    public void deleteQuiz(Long quizId);

    public List<Quiz> getActiveQuizzesAdmin();

    public List<Quiz> getCategoryActiveQuizzesAdmin(String category);

    public List<Quiz> getActiveQuizzesUser();

    public List<Quiz> getCategoryActiveQuizzesUser(String category);
}
