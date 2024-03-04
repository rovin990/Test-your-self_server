package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.entities.Question;
import com.kick.it.kickit.repository.QuestionRepo;
import com.kick.it.kickit.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    @Override
    public Question saveQuestion(Question question) {
        return questionRepo.save(question);
    }

    @Override
    public Question getQuestion(Long questionId) {
        return questionRepo.findById(questionId).get();
    }

    @Override
    public List<Question> getQuestions() {
        return questionRepo.findAll();
    }

    @Override
    public Question updateQuestion(Question question) {
        return questionRepo.saveAndFlush(question);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionRepo.deleteById(questionId);
    }

    @Override
    public List<Question> getQuestionByQuizId(Long quizId) {
        return questionRepo.findAll().stream().filter(question -> question.getQuizList().contains(quizId)).toList();
    }

    @Override
    public Question getQuestionByQuestionId(Long questionId) {
        return questionRepo.findById(questionId).get();
    }

}
