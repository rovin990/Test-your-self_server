package com.kick.it.kickit;

import com.kick.it.kickit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizApplication {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
	}
}
