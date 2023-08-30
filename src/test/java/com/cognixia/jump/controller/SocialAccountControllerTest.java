package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.SameUserAndPlatformException;
import com.cognixia.jump.model.SocialAccount;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.SocialAccountRepository;
import com.cognixia.jump.service.SocialAccountService;


@WebMvcTest(SocialAccountController.class)
//@ExtendWith(MockitoExtension.class)
public class SocialAccountControllerTest {
	
	private static final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private SocialAccountService service;
	
	@MockBean
	private SocialAccountRepository repo;
	
	@InjectMocks
	private SocialAccountController controller;
	
	@Test
	public void testGetAccounts() throws Exception {
		String uri = STARTING_URI + "/account";
		
		User newUser =  new User(1, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);
		List<SocialAccount> accounts = new ArrayList<>();
		accounts.add( new SocialAccount(1, "group2_facebook", "Facebook description", SocialAccount.Platform.FACEBOOK, newUser));
		accounts.add( new SocialAccount(2, "group2_instagram", "Instagram description", SocialAccount.Platform.INSTAGRAM, newUser));
		
		when( controller.getAccounts() ).thenReturn(accounts);
		
//		List<SocialAccount> result = controller.getAccounts();
		
		mvc.perform( get(uri) )
				.andDo( print() )
				.andExpect( status().isOk() )
				.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) )
				.andExpect( jsonPath( "$.length()" ).value( accounts.size() ) )
				.andExpect( jsonPath( "$[0].id" ).value( accounts.get(0).getId() ) )
				.andExpect( jsonPath( "$[0].accountName" ).value( accounts.get(0).getAccountName() ) )
				.andExpect( jsonPath( "$[0].description" ).value( accounts.get(0).getDescription() ) )
				.andExpect( jsonPath( "$[0].platformName" ).value( accounts.get(0).getPlatform() ) )
				.andExpect( jsonPath( "$[0].user" ).value( accounts.get(0).getUser() ) )
				.andExpect( jsonPath( "$[1].id" ).value( accounts.get(1).getId() ) )
				.andExpect( jsonPath( "$[1].accountName" ).value( accounts.get(1).getAccountName() ) )
				.andExpect( jsonPath( "$[1].description" ).value( accounts.get(1).getDescription() ) )
				.andExpect( jsonPath( "$[1].platformName" ).value( accounts.get(1).getPlatform() ) )
				.andExpect( jsonPath( "$[1].user" ).value( accounts.get(1).getUser() ) );
		
//		verify( repo, times(1) ).findAll();
//		verifyNoMoreInteractions(repo);
	};
	
//	@Test
//	public void testGetAccountsById() throws Exception {
//		
//		String uri = STARTING_URI + "/account/{id}";
//
//		int id = 1;
//		User newUser =  new User(id, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);
//		SocialAccount account = new SocialAccount(1, "group2_facebook", "Facebook description", SocialAccount.Platform.FACEBOOK, newUser);
//		
//		when( repo.findById(id) ).thenReturn(Optional.of(account));
//		
//		mvc.perform( get(uri) )
//				.andDo( print() )
//				.andExpect( status().isOk() )
//				.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE) )
//				.andExpect( jsonPath( "$[0].id" ).value( account.getId() ) )
//				.andExpect( jsonPath( "$[0].accountName" ).value( account.getAccountName() ) )
//				.andExpect( jsonPath( "$[0].description" ).value( account.getDescription() ) )
//				.andExpect( jsonPath( "$[0].platformName" ).value( account.getPlatform() ) )
//				.andExpect( jsonPath( "$[0].user" ).value( account.getUser() ) );
//		
//		verify( repo, times(1) ).findById(id);
//		verifyNoMoreInteractions(repo);
//	};
//	
//	@Test
//	public void testGetAccountsByIdNotFound() throws Exception {
//		
//		String uri = STARTING_URI + "/account/{id}";
//
//		int id = 1;
//		
//		when( repo.findById(id) ).thenThrow( new ResourceNotFoundException("Account was not found"));
//		
//		mvc.perform( get(uri) )
//				.andDo( print() )
//				.andExpect( status().isNotFound() );
//		
//		verify( repo, times(1) ).findById(id);
//		verifyNoMoreInteractions(repo);
//	};
//	
//	@Test
//	public void testCreateAccount() throws Exception {
//		
//		String uri = STARTING_URI + "/account";
//		
//		User newUser =  new User(1, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);
//		SocialAccount account = new SocialAccount(1, "group2_facebook", "Facebook description", SocialAccount.Platform.FACEBOOK, newUser);
//	
//		when( repo.save( Mockito.any(SocialAccount.class) ) ).thenReturn(account);
//		
//		mvc.perform( post(uri)
//					.contentType( MediaType.APPLICATION_JSON_VALUE) )
//			.andDo( print() )
//			.andExpect( status().isCreated() )
//			.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE));
//		
//		verify( repo, times(1) ).save(Mockito.any(SocialAccount.class));
//		verifyNoMoreInteractions(repo);
//	}
//	
//	@Test
//	public void testCreateAccountSameUserAndPlatform() throws Exception {
//		
//		String uri = STARTING_URI + "/account";
//		
//		when( repo.save( Mockito.any(SocialAccount.class) ) ).thenThrow(new SameUserAndPlatformException() );
//	
//		mvc.perform( post(uri) )
//			.andDo( print() )
//			.andExpect( status().isConflict() );
//
//		verify( repo, times(1) ).save(Mockito.any(SocialAccount.class));
//		verifyNoMoreInteractions(repo);
//	}
//	
//	
//	@Test
//	public void testUpdateAccount() throws Exception {
//		
//		String uri = STARTING_URI + "/account";
//		
//		User newUser =  new User(1, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);
//		SocialAccount account = new SocialAccount(1, "group2_facebook", "Facebook description", SocialAccount.Platform.FACEBOOK, newUser);
//		
//		when( repo.save( Mockito.any(SocialAccount.class) ) ).thenReturn(account);
//		
//		mvc.perform( put(uri)
//					.contentType( MediaType.APPLICATION_JSON_VALUE) )
//			.andDo( print() )
//			.andExpect( status().isCreated() )
//			.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE));
//		
//		verify( repo, times(1) ).save(Mockito.any(SocialAccount.class));
//		verifyNoMoreInteractions(repo);
//	}
//	
//	@Test
//	public void testUpdateAccountSameUserAndPlatform() throws Exception {
//		
//		String uri = STARTING_URI + "/account";
//		
//		when( repo.save( Mockito.any(SocialAccount.class) ) ).thenThrow(new SameUserAndPlatformException() );
//	
//		mvc.perform( put(uri) )
//			.andDo( print() )
//			.andExpect( status().isConflict() );
//
//		verify( repo, times(1) ).save(Mockito.any(SocialAccount.class));
//		verifyNoMoreInteractions(repo);
//	}
//	
//	@Test
//	public void testUpdateAccountNotFound() throws Exception {
//		
//		String uri = STARTING_URI + "/account";
//		
//		when( repo.save( Mockito.any(SocialAccount.class) ) ).thenThrow(new ResourceNotFoundException("Account was not found") );
//	
//		mvc.perform( put(uri) )
//			.andDo( print() )
//			.andExpect( status().isNotFound() );
//
//		verify( repo, times(1) ).save(Mockito.any(SocialAccount.class));
//		verifyNoMoreInteractions(repo);
//	}
//	
//	@Test
//	public void testDeleteAccount() throws Exception {
//		
//		String uri = STARTING_URI + "/account/{id}";
//		
//		int id = 1;
//		User newUser =  new User(1, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);
//		SocialAccount account = new SocialAccount(id, "group2_facebook", "Facebook description", SocialAccount.Platform.FACEBOOK, newUser);
//		
//		when(repo.existsById(id)).thenReturn(true);
//
//	    when(repo.findById(id)).thenReturn(Optional.of(account));
//
//	    ResponseEntity<?> response = controller.deleteAccount(id);
//
//	    assertEquals(HttpStatus.OK, response.getStatusCode());
//	    assertEquals("Account was deleted", response.getBody());
//	    
//	    verify(repo).deleteById(id);
//	}
//	
//	@Test
//	public void testDeleteAccountNotfound() throws Exception {
//		
//		String uri = STARTING_URI + "/account/{id}";
//		
//		int id = 1;
//		
//		when(repo.existsById(id)).thenThrow( new ResourceNotFoundException("Account was not found") );
//		
//		mvc.perform( delete(uri, id) )
//			.andDo( print() )
//			.andExpect( status().isNotFound() );
//
//		verify( repo, times(1) ).existsById(id);
//		verifyNoMoreInteractions(repo);
//	}
	
}
