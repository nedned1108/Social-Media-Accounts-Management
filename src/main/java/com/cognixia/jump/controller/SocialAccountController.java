package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.SameUserAndPlatformException;
import com.cognixia.jump.model.SocialAccount;

import com.cognixia.jump.service.SocialAccountService;

import com.cognixia.jump.repository.SocialAccountRepository;

@RequestMapping("/api")
@RestController
public class SocialAccountController {

	@Autowired
	SocialAccountService service;

	@GetMapping("/account")
	public List<SocialAccount> getAccounts() {
		return service.getAccounts();
	}

	@GetMapping("/account/{id}")
	public ResponseEntity<?> getAccountsById(@PathVariable int id) throws ResourceNotFoundException {

		SocialAccount found = service.getAccountById(id);

		return ResponseEntity.status(200).body(found);
	}

	// create account throws sameUser exception
	@PostMapping("/account")
	public ResponseEntity<SocialAccount> createAccount(@Valid @RequestBody SocialAccount account)
			throws SameUserAndPlatformException {

		SocialAccount created = service.createAccount(account);

		return ResponseEntity.status(201).body(created);
	}

	// create account throws sameUser exception
	@PutMapping("/account")
	public ResponseEntity<SocialAccount> updateAccount(@Valid @RequestBody SocialAccount account)
			throws ResourceNotFoundException, SameUserAndPlatformException {

		Optional<SocialAccount> updated = Optional.ofNullable(service.updateAccount(account));

		if (updated.isEmpty()) {
			throw new ResourceNotFoundException("Account can't be found");
		} else {
			return ResponseEntity.status(200)
					.body(updated.get());
		}
	}

	@DeleteMapping("/account/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable int id) throws ResourceNotFoundException {

		service.deleteAccount(id);
		return ResponseEntity.status(200).body("Deleted account");
	}

}
