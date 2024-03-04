package com.kick.it.kickit;

import com.kick.it.kickit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizeApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(QuizeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		User user = new User();
//		user.setName("rovin");
//		user.setEmail("rovin@gmail.com");
//		user.setUsername("rovin990");
//		user.setMobile("7292828357");
//
//		UserRole userRole = new UserRole();
//		userRole.setUser(user);
//
//		Role role = new Role();
//		role.setRoleId(100L);
//		role.setRoleName("NORMAL");
//		userRole.setRole(role);
//
//		Set<UserRole> userRoles = new HashSet<>();
//		userRoles.add(userRole);
//
//		userService.createUser(user,userRoles);
	}
}
