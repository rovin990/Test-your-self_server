package com.kick.it.kickit.controllers;

import com.kick.it.kickit.entities.Category;
import com.kick.it.kickit.entities.Quiz;
import com.kick.it.kickit.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;


    @GetMapping("/{filter}") // fetch or get by titles
    public ResponseEntity<?> getQuizzes(@PathVariable String filter){
        try{
            List<Quiz> localQuizzes =quizService.getQuizzes().stream().filter(item->{
                if(filter.equalsIgnoreCase("All"))return true;
                else if(item.getCategory().equalsIgnoreCase(filter) || item.getTitle().equalsIgnoreCase(filter)){
                    System.out.println("filter "+filter);
                    return true;
                }
                else {
                    return false;
                }
            }).toList();
           // localQuizzes.stream().forEach(System.out::println);
            return ResponseEntity.status(HttpStatus.OK).body(localQuizzes);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    @GetMapping // fetch or get by quizId
    public ResponseEntity<?> getQuizByQuizId(@RequestParam Long quizId){
        System.out.println("QuizId :"+quizId);
        try{
            Quiz local =quizService.getQuiz(quizId);
            return ResponseEntity.status(HttpStatus.OK).body(local);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @PostMapping //save
    public ResponseEntity<?> saveQuiz(@RequestBody Quiz quiz){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                quiz.setCreatedBy(currentUserName);
            }
            quiz.setQuestionLeft(quiz.getNoOfQuestion());
            quiz.setCreatedDate(LocalDate.now());
            System.out.println("hello "+quiz);
            Quiz local=quizService.saveQuiz(quiz);
            return ResponseEntity.status(HttpStatus.CREATED).body(local);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PutMapping //update
    public ResponseEntity<?> updateQuiz(@RequestBody Quiz quiz){
        try{
            Quiz local=quizService.updateQuiz(quiz);
            return ResponseEntity.status(HttpStatus.CREATED).body(local);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @DeleteMapping("/{qId}") // delete
    public ResponseEntity<String> deleteQuiz(@PathVariable Long qId){
        ResponseEntity response= null;
        try{
            quizService.deleteQuiz(qId);
            response=ResponseEntity.status(HttpStatus.OK).body("Successfully deleted Quiz id : "+qId);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
        return response;
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveQuizzesAdmin(){
        List<Quiz> localQuizzes=null;
        try{
            localQuizzes =quizService.getActiveQuizzesAdmin();
            return ResponseEntity.status(HttpStatus.OK).body(localQuizzes);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/category/active/{category}")
    public ResponseEntity<?> getCategoryActiveQuizzesAdmin(@PathVariable String category){
        List<Quiz> localQuizzes=null;
        try{
            localQuizzes =quizService.getCategoryActiveQuizzesAdmin(category);
            return ResponseEntity.status(HttpStatus.OK).body(localQuizzes);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/active/user")
    public ResponseEntity<?> getActiveQuizzesUser(){
        List<Quiz> localQuizzes=null;
        try{
            localQuizzes =quizService.getActiveQuizzesUser();
            return ResponseEntity.status(HttpStatus.OK).body(localQuizzes);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @GetMapping("/category/active/user/{category}")
    public ResponseEntity<?> getCategoryActiveQuizzesUser(@PathVariable String category){
        List<Quiz> localQuizzes=null;
        try{
            localQuizzes =quizService.getCategoryActiveQuizzesUser(category);
            return ResponseEntity.status(HttpStatus.OK).body(localQuizzes);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
