package com.kick.it.kickit.controllers;

import com.kick.it.kickit.entities.ContactUs;
import com.kick.it.kickit.services.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact-us")
public class ContactUsController {

    @Autowired
    ContactUsService contactUsService;

    @PostMapping
    public ResponseEntity<?> contactus(@RequestBody ContactUs contactUs){
        try{
           String refNo= contactUsService.saveQuery(contactUs);
            return ResponseEntity.status(HttpStatus.OK).body(refNo);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
