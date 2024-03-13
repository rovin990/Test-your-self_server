package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.entities.Question;
import com.kick.it.kickit.entities.Quiz;
import com.kick.it.kickit.repository.QuestionRepo;
import com.kick.it.kickit.repository.QuizRepository;
import com.kick.it.kickit.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    QuizRepository quizRepository;

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
       List<Question> localList= questionRepo.findAll();
        List<Question> result = new ArrayList<>();
       for(int i=0;i<localList.size();i++){
           String quizStr = localList.get(i).getQuizIds().substring(0,localList.get(i).getQuizIds().length()-1);

           String[] quizIds= quizStr.split(",");
           for(int j=0;j<quizIds.length;j++){
               System.out.println(quizIds[j]);
               if(quizIds[j]!=null){
                   if(Long.parseLong(quizIds[j])==quizId){
                       result.add(localList.get(i));
                   }
                   else {
                       System.out.println("Not containing quiz id");
                   }
               }
           }
       }
        //System.out.println("localList"+localList);
//        List<Question> result =  localList.stream().filter(question -> {
//            String quizStr = question.getQuizIds().substring(0,question.getQuizIds().length()-1);
//            System.out.println(quizStr);
//            String[] quizIds= quizStr.split(",");
//            System.out.println(quizIds);
//            for(String qId : quizIds){
//                System.out.println(qId);
//               if(qId!=null)
//                   if(Long.getLong(qId).equals(quizId))
//                    return true;
//            }
//            return false;
//        }).toList();
        System.out.println("result"+result);
        return result;
    }

    @Override
    public Question getQuestionByQuestionId(Long questionId) {
        return questionRepo.findById(questionId).get();
    }

    @Override
    public void addToQuiz(String[] quizIds) {
        for(String quizId : quizIds){
            Quiz local=quizRepository.findById(Long.getLong(quizId)).get();

            local.setQuestionLeft(local.getQuestionLeft()+1);
        }
    }

}
