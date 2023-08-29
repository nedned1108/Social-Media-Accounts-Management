package com.cognixia.jump.controller;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.repository.UserRepository;

@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@PostMapping("/user")
	public ResponseEntity<?> createUser( @RequestBody User user ) {
		
		user.setId(null);
		
		user.setPassword( encoder.encode( user.getPassword() ) );
		
		User created = repo.save(user);
		
		return ResponseEntity.status(201).body(created);
	}
	
}
