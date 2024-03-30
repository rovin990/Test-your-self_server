package com.kick.it.kickit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException exception){
        return
                new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
