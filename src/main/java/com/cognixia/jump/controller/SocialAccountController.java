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
	SocialAccountRepository repo;

	@Autowired
	SocialAccountService service;

	@GetMapping("/account")
	public List<SocialAccount> getAccounts() {
		return service.getAccounts();
	}

	@GetMapping("/account/{id}")
	public ResponseEntity<?> getAccountsById(@PathVariable int id) throws ResourceNotFoundException {

		Optional<SocialAccount> found = service.getAccountById(id);

		if (found.isEmpty()) {
			throw new ResourceNotFoundException("SocialAccount");
		}

		return ResponseEntity.status(200).body(found.get());
	}

	// create account throws sameUser exception
	@PostMapping("/account")
	public ResponseEntity<?> createAccount(@Valid @RequestBody SocialAccount account)
			throws SameUserAndPlatformException {

		Optional<SocialAccount> foundAccountName = repo.findByAccountName(account.getAccountName());

		Optional<SocialAccount> foundPlatform = repo.findByPlatform(account.getPlatformName());

		// make sure each account created has a unique account name or platform , if not
		// checked, will end up with 409 error

		if (!foundAccountName.isEmpty() && !foundPlatform.isEmpty()) {
			throw new SameUserAndPlatformException();
		}

		SocialAccount created = service.createAccount(account);

		return ResponseEntity.status(201).body(created);
	}

	// create account throws sameUser exception
	@PutMapping("/account")
	public ResponseEntity<?> updateAccount(@Valid @RequestBody SocialAccount account)
			throws ResourceNotFoundException, SameUserAndPlatformException {

		Optional<SocialAccount> foundAccountName = repo.findByAccountName(account.getAccountName());

		Optional<SocialAccount> foundPlatform = repo.findByPlatform(account.getPlatformName());

		if (foundAccountName.isEmpty() && foundPlatform.isEmpty()) {
			throw new SameUserAndPlatformException();
		}

		Optional<SocialAccount> updated = service.updateAccount(account);

		if (updated.isEmpty()) {
			throw new ResourceNotFoundException("Account can't be found");
		} else {
			return ResponseEntity.status(200)
					.body(updated.get());
		}
	}

	@DeleteMapping("/account/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable int id) throws ResourceNotFoundException {

		if (service.deleteAccount(id)) {

			// will return the account we just deleted in the response
			SocialAccount deleted = repo.findById(id).get();

			return ResponseEntity.status(200).body(deleted);
		} else {
			throw new ResourceNotFoundException("Account could not be found nor deleted");
		}

	}

}
