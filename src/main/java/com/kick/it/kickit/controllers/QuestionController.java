package com.kick.it.kickit.controllers;

import com.kick.it.kickit.entities.Question;
import com.kick.it.kickit.repository.QuestionRepo;
import com.kick.it.kickit.responses.QuestionResponse;
import com.kick.it.kickit.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/{filter}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable String filter){

        List<Question> allQuestion=questionService.getQuestions();
        if(!filter.equalsIgnoreCase("all")){

            allQuestion=allQuestion.stream().filter(question -> {
                return (question.getExamTags().contains(filter) ||
                        question.getId().equals(Long.valueOf(filter)));
            }).toList();
        }

        return ResponseEntity.status(HttpStatus.OK).body(allQuestion);
    }


    @DeleteMapping("/{qId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long qId){
        ResponseEntity response = null;

        questionService.deleteQuestion(qId);

        response = ResponseEntity.status(HttpStatus.OK).body("Successfully question no "+qId+" deleted");
        return  response;
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> saveUpdate(@RequestBody Question question){
        QuestionResponse questionResponse=new QuestionResponse();
        ResponseEntity response=null;
        try{
            Authentication principal =SecurityContextHolder.getContext().getAuthentication();
            if(question.getId()==null){

                question.setCreatedDate(LocalDate.now());
                question.setCreatedBy(principal.getName());
                questionService.saveQuestion(question);
                List<Question> listUpdate= new ArrayList<>();
                listUpdate.add(question);
                questionResponse.setQuestionList(listUpdate);
                questionResponse.setMassage("Question saved with id : "+question.getId());
                response= ResponseEntity.status(HttpStatus.CREATED).body(questionResponse);
            }
            else{
                Question local = questionService.getQuestion(question.getId());
                local.setUpdatedDate(LocalDate.now());
                local.setUpdatedBy(principal.getName());
                questionService.updateQuestion(local);
                List<Question> listUpdate= new ArrayList<>();
                listUpdate.add(local);
                questionResponse.setQuestionList(listUpdate);
                questionResponse.setMassage("Question updated");
                response= ResponseEntity.status(HttpStatus.OK).body(questionResponse);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<?> getQuestionByQuizId(@PathVariable Long quizId){

        try{
            List<Question> allQuestion=questionService.getQuestionByQuizId(quizId);
            return ResponseEntity.status(HttpStatus.OK).body(allQuestion);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
}
