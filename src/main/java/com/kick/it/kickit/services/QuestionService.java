package com.kick.it.kickit.services;

import com.kick.it.kickit.entities.Question;
import com.kick.it.kickit.entities.Quiz;

import java.util.List;

public interface QuestionService {

    public Question saveQuestion(Question question);

    public  Question getQuestion(Long questionId);

    public List<Question> getQuestions();

    public Question updateQuestion(Question question);

    public void deleteQuestion(Long questionId);

    public List<Question> getQuestionByQuizId(Long quizId);

    public Question getQuestionByQuestionId(Long questionId);

    public void addToQuiz(String[] quizIds);
}
