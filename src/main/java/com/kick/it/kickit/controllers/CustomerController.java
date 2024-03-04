package com.kick.it.kickit.controllers;

import com.kick.it.kickit.entities.Customer;
import com.kick.it.kickit.responses.UserResponse;
import com.kick.it.kickit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class CustomerController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}") //fetch-user
    public ResponseEntity<Customer> getUser(@PathVariable String username){
        ResponseEntity response=null;
        try{
            Customer local= userService.getUser(username);
            if(local!=null){
                response=ResponseEntity.status(HttpStatus.FOUND).body(local);
            }
            else{
                response=ResponseEntity.status(HttpStatus.NOT_FOUND).body(local);
            }
        }catch (Exception e){
            e.getMessage();
        }
       return response;

    }

    @PutMapping //update user
    public ResponseEntity<UserResponse> updateUser(@RequestBody Customer customer){
        ResponseEntity response=null;
        System.out.println("start");
        try{
           Customer local= userService.getUser(customer.getUsername());
           System.out.println("start local "+local);
           UserResponse userResponse = new UserResponse();
           local.setMobile(customer.getMobile());
           local.setEmail(customer.getEmail());
           local.setName(customer.getName());
           local.setPassword(customer.getPassword());
           Customer updatedlocal= userService.updateUser(local);
           System.out.println(updatedlocal);
           if(updatedlocal!=null){
               userResponse.setStatus("204 UPDATED");
               userResponse.setMassage("User updated successfully");
               userResponse.setUsername(updatedlocal.getUsername());
               System.out.println("204 updated"+userResponse);
               response=ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
           }
           else {
               userResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
               userResponse.setMassage("User is not updated successfully");
               userResponse.setUsername(updatedlocal.getUsername());
               System.out.println("User is not updated successfully");
               response=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userResponse);
           }


        }catch (Exception e){
            e.getMessage();
        }
        return  response;
    }

    @DeleteMapping("/{username}") //delete user
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String username){
        ResponseEntity response=null;
        UserResponse userResponse = new UserResponse();
        try{
            userService.deleteUser(username);
            userResponse.setUsername(username);
            userResponse.setMassage("User deleted");
            userResponse.setStatus(HttpStatus.NOT_FOUND.toString());
            response=ResponseEntity.status(HttpStatus.NOT_FOUND).body(userResponse);
        }catch (Exception e){
            userResponse.setUsername(username);
            userResponse.setMassage(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            userResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userResponse);
        }
        return response;
    }
}
