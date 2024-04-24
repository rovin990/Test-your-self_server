package com.kick.it.kickit.controllers;

import com.kick.it.kickit.entities.UserQuizDetail;
import com.kick.it.kickit.services.UserQuizDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test-response")
public class UserQuizDetailController {
    @Autowired
    UserQuizDetailService userQuizDetailService;

    @GetMapping("/{quizId}/{attemptNo}")
    public ResponseEntity<?> getTestResponse(@PathVariable Long quizId,@PathVariable int attemptNo){
        try{
            List<UserQuizDetail> localList=userQuizDetailService.getTestResponse(quizId,attemptNo);
            if(localList.size()>0){
                return ResponseEntity.status(HttpStatus.OK).body(localList);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no test response with given quizId : "+quizId);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAttemptedQuizResponse(){
        try{
            List<UserQuizDetail> testResponses=userQuizDetailService.getAllAttemptedQuizResponse();
            if(testResponses!=null){
                return ResponseEntity.status(HttpStatus.OK).body(testResponses);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No quiz attempted");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createTestResponse(@RequestBody UserQuizDetail userQuizDetail){
        //System.out.println(userQuizDetail);
//        if(userQuizDetail.getResponses().size()==0)return ResponseEntity.status(HttpStatus.OK).body("User");
        try{

            UserQuizDetail savedUserQuizDetail=userQuizDetailService.createTestResponse(userQuizDetail);
            if(savedUserQuizDetail!=null){
                return ResponseEntity.status(HttpStatus.OK).body(savedUserQuizDetail);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return null;
    }

    @GetMapping("/rankers")
    public ResponseEntity<?> getRankerByQuizId(@RequestParam("quizId") Long quizId,@RequestParam("attemptNo") int attemptNo){
        try{
            System.out.println("quixId"+quizId+" attemptNo "+attemptNo);
            List<UserQuizDetail> response = userQuizDetailService.getAllTestResponseForRanking(quizId, attemptNo);

            return  ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
