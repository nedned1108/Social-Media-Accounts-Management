package com.cognixia.jump.controller;

import java.util.Map;
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
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user, @RequestBody Map<String, String> userBio,
			@RequestBody Map<String, String> companyName)
			throws ResourceNotFoundException {

		String bio = userBio.get("bio");
		String name = companyName.get("company_name");

		if (repo.existsById(user.getId())) {
			user.setBio(bio);
			user.setName(name);
			User updated = repo.save(user);

			return ResponseEntity.status(200).body(updated);
		}

		throw new ResourceNotFoundException("User Not Found");
	}

}
