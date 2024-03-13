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
    public ResponseEntity<List<Quiz>> getQuizzes(@PathVariable String filter){
        ResponseEntity response=null;
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
            response=ResponseEntity.status(HttpStatus.OK).body(localQuizzes);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
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
    public ResponseEntity<Category> saveQuiz(@RequestBody Quiz quiz){
        ResponseEntity response= null;
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
            response=ResponseEntity.status(HttpStatus.CREATED).body(local);
        }catch (Exception e){
            e.getMessage();
        }
        return response;
    }

    @PutMapping //update
    public ResponseEntity<Category> updateQuiz(@RequestBody Quiz quiz){
        ResponseEntity response= null;
        try{
            Quiz local=quizService.updateQuiz(quiz);
            response=ResponseEntity.status(HttpStatus.CREATED).body(local);
        }catch (Exception e){
            e.getMessage();
        }
        return response;
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
        }
        return response;
    }

    @GetMapping("/active")
    public ResponseEntity<List<Quiz>> getActiveQuizzes(){
        ResponseEntity response=null;
        try{
            List<Quiz> localQuizzes =quizService.getActiveQuizzes();
            response=ResponseEntity.status(HttpStatus.OK).body(localQuizzes);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("/category/active/{category}")
    public ResponseEntity<List<Quiz>> getCategoryActiveQuizzes(@PathVariable String category){
        ResponseEntity response=null;
        try{
            List<Quiz> localQuizzes =quizService.getCategoryActiveQuizzes(category);
            response=ResponseEntity.status(HttpStatus.OK).body(localQuizzes);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
