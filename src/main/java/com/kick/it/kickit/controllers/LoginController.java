package com.kick.it.kickit.controllers;

import com.kick.it.kickit.entities.Customer;
import com.kick.it.kickit.repository.CustomerRepository;
import com.kick.it.kickit.repository.RoleRepo;
import com.kick.it.kickit.services.QuestionService;
import com.kick.it.kickit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RoleRepo roleRepo;


    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;


    @PostMapping("/register")
    public ResponseEntity<Customer> registerUser(@RequestBody Customer customer){
        ResponseEntity response=null;
        Customer savedCustomer =null;
        try{

            savedCustomer= userService.createUser(customer);
            response=ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);


        }
        catch (Exception e){
            response=ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return response;
    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Customer customer = customerRepository.findByUsername(authentication.getName());

        if (customer!=null) {
            return customer;
        } else {
            return null;
        }
    }
}