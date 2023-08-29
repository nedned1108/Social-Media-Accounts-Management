package com.cognixia.jump.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	UserRepository repo;

	@Autowired
	PasswordEncoder encoder;

	@PostMapping("/user")
	public ResponseEntity<?> createUser(@RequestBody User user) {

		user.setId(null);

		user.setPassword(encoder.encode(user.getPassword()));

		User created = repo.save(user);

		return ResponseEntity.status(201).body(created);
	}

	@PutMapping("/user")
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user) throws ResourceNotFoundException {

		if (repo.existsById(user.getId())) {

			User updated = repo.save(user);

			// check if the address matches the student and reply back with a proper
			// response if you attempt
			// to update the address and its id does not match what the student has

			return ResponseEntity.status(200).body(updated);
		}

		throw new ResourceNotFoundException("User Not Found");
	}

}
