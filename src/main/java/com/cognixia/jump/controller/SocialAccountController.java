package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RequestMapping("/api")
@RestController
public class SocialAccountController {
	
	@Autowired
	SocialAccountService service;

	@Operation( summary = "Get all the accounts in the social_account table",
			 description = "Get all accounts from the social_account table in the social_management database. "
			 		+ "Each account grabbed has an id, account_name, platform_name, description, and user"
			 )

	@GetMapping("/account")
	public List<SocialAccount> getAccounts() {
		return service.getAccounts();
	}

	@Operation( summary = "Get an account by Id",
			 description = "Get an account by its Id from the social_account table in the social_management database."
			 		+"ach account grabbed has an id, account_name, platform_name, description, and user"
			 )
	@ApiResponses(
			@ApiResponse(responseCode="200",
					description="Account has been found")
			)
	
	@GetMapping("/account/{id}")
	public ResponseEntity<?> getAccountsById(@PathVariable int id) throws ResourceNotFoundException {

		SocialAccount found = service.getAccountById(id);
		
		return ResponseEntity.status(200).body(found);
	}

	@Operation( summary = "Create a new account",
			 description = "Create a new account from the social_account table in the social_management database."
			 		+ "Each account created has an id, account_name, platform_name, description, and user"
			 )                   
	@ApiResponses(
			@ApiResponse(responseCode="201",
			description="Account has been created")
			)
	// create account throws sameUser exception
	@PostMapping("/account")
	public ResponseEntity<SocialAccount> createAccount(@Valid @RequestBody SocialAccount account)
			throws SameUserAndPlatformException {
		
		SocialAccount created = service.createAccount(account);

		return ResponseEntity.status(201).body(created);
	}
	
	@Operation( summary = "Update an account",
	 description = "Update an account from the social_account table in the social_management database."
	 		+ "Each account updated has an id, account_name, platform_name, description, and user"
	 )
			
	@ApiResponses(
			@ApiResponse(responseCode="200",
					description="Account has been updated")
			)
	
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

	@Operation( summary = "Delete an account by Id",
			 description = "Deletes an account by its Id from the social_account table in the social_management database."
			 		+ "Each account grabbed has an id, account_name, platform_name, description, and user"	 
			 )
	@ApiResponses(
			@ApiResponse(responseCode="200",
					description="Account has been deleted")
			)
	
	@DeleteMapping("/account/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable int id) throws ResourceNotFoundException {

		service.deleteAccount(id);
		return ResponseEntity.status(200).body("Deleted account");
	}

}
