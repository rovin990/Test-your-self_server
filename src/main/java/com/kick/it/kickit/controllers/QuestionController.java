package com.kick.it.kickit.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kick.it.kickit.entities.OptionsImage;
import com.kick.it.kickit.entities.Question;
import com.kick.it.kickit.entities.QuestionImage;
import com.kick.it.kickit.repository.QuestionRepo;
import com.kick.it.kickit.responses.QuestionResponse;
import com.kick.it.kickit.services.ImageService;
import com.kick.it.kickit.services.QuestionService;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;


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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<QuestionResponse> saveUpdate(@RequestParam("question") String questionStr,
                                                       @RequestParam("formImages")MultipartFile... formImages) throws JsonProcessingException {
        QuestionResponse questionResponse=new QuestionResponse();
        Question question=objectMapper.readValue(questionStr,Question.class);
//        Question(id=null, title=Test, explanation=Tesst, answer=apptitude.png, questionType=, isImageOps=false, examTags=[test], options=Options(id=null, option1=apptitude.png, option2=core_java.png, option3=dart-board.png, option4=document.png, option5=null, optionsImage=null), questionImage=null, topic=tst, subject=test, quizList=[28], createdDate=null, createdBy=null, updatedDate=null, updatedBy=null)
//        {"title":"Test","explanation":"Tesst","answer":"apptitude.png","questionType":"","options":{"option1":"apptitude.png","option2":"core_java.png","option3":"dart-board.png","option4":"document.png"},"examTags":["test"],"topic":"tst","subject":"test","quizList":[28],"image":"java_stream.png","isImageOps":true}

        ResponseEntity response=null;
        try{
            Authentication principal =SecurityContextHolder.getContext().getAuthentication();
            if(question.getId()==null){
                System.out.println("New question "+question);
                question.setCreatedDate(LocalDate.now());
                question.setCreatedBy(principal.getName());
                if(formImages.length==1 || formImages.length>0)question.setQuestionImage(new QuestionImage(formImages[0].getOriginalFilename(),formImages[0].getBytes()));
                if(formImages.length==5){
                    System.out.println(formImages[1]);
                    System.out.println(formImages[2]);
                    System.out.println(formImages[3]);
                    question.getOptions().setOptionsImage(new OptionsImage(formImages[1].getBytes(),formImages[2].getBytes(),formImages[3].getBytes(),formImages[4].getBytes()));
                }

                questionService.saveQuestion(question);
                String[] quizIds = question.getQuizIds().split(",");
                questionService.addToQuiz(quizIds);

                List<Question> listUpdate= new ArrayList<>();
                listUpdate.add(question);
                questionResponse.setQuestionList(listUpdate);
                questionResponse.setMassage(question.getId().toString());
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

    @PutMapping("/")
    public ResponseEntity<QuestionResponse> createQuestionWithOutImages(@RequestParam("question") String questionStr) throws JsonProcessingException {
        QuestionResponse questionResponse=new QuestionResponse();
        Question question=objectMapper.readValue(questionStr,Question.class);
        ResponseEntity response=null;
        try{
            Authentication principal =SecurityContextHolder.getContext().getAuthentication();
            if(question.getId()==null){
                System.out.println("New question "+question);
                question.setCreatedDate(LocalDate.now());
                question.setCreatedBy(principal.getName());
                questionService.saveQuestion(question);

                List<Question> listUpdate= new ArrayList<>();
                listUpdate.add(question);
                questionResponse.setQuestionList(listUpdate);
                questionResponse.setMassage(question.getId().toString());
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

    @PostMapping(value = "/import",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadQuestionFile(@RequestParam("question_exl") MultipartFile readExcelDataFile){
        try {
            System.out.println(readExcelDataFile.getName());
            int noOfSavedQuestion=questionService.uploadQuestionFile(readExcelDataFile);

            return ResponseEntity.status(HttpStatus.OK).body(noOfSavedQuestion);

        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }







}
