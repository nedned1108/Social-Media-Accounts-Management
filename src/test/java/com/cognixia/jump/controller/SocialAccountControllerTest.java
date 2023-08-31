package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
//import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.SameUserAndPlatformException;
import com.cognixia.jump.model.SocialAccount;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.MyUserDetailsService;
//import com.cognixia.jump.repository.SocialAccountRepository;
import com.cognixia.jump.service.SocialAccountService;
import com.cognixia.jump.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(SocialAccountController.class)
public class SocialAccountControllerTest {
	
	private static final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private SocialAccountService service;
	
	@MockBean
	private MyUserDetailsService myUserDetailsService;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	@InjectMocks
	private SocialAccountController controller;
	
	@Test
	public void testGetAccounts() throws Exception {
		String uri = STARTING_URI + "/account";
		
		User newUser =  new User(1, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);

		SocialAccount account1 = new SocialAccount();
		account1.setAccountName("group2_facebook");
		account1.setDescription("Facebook description");
		account1.setPlatformName(SocialAccount.Platform.FACEBOOK);
		account1.setUser(newUser);
		SocialAccount account2 = new SocialAccount();
		account2.setAccountName("group2_instagram");
		account2.setDescription("Instagram description");
		account2.setPlatformName(SocialAccount.Platform.INSTAGRAM);
		account2.setUser(newUser);
		
		List<SocialAccount> accounts = new ArrayList<>();
		accounts.add(account1);
		accounts.add(account2);
		
		when( service.getAccounts() ).thenReturn(accounts);
		
		mvc.perform( get(uri).with(user("group2").password("password")) )
				.andDo( print() )
				.andExpect( status().isOk() )
				.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) )
				.andExpect( jsonPath( "$.length()" ).value( accounts.size() ) )
				.andExpect( jsonPath( "$[0].id" ).value( accounts.get(0).getId() ) )
				.andExpect( jsonPath( "$[0].accountName" ).value( accounts.get(0).getAccountName() ) )
				.andExpect( jsonPath( "$[0].description" ).value( accounts.get(0).getDescription() ) )
				.andExpect( jsonPath( "$[0].platformName" ).value( accounts.get(0).getPlatformName().toString() ) )
				.andExpect( jsonPath( "$[1].id" ).value( accounts.get(1).getId() ) )
				.andExpect( jsonPath( "$[1].accountName" ).value( accounts.get(1).getAccountName() ) )
				.andExpect( jsonPath( "$[1].description" ).value( accounts.get(1).getDescription() ) )
				.andExpect( jsonPath( "$[1].platformName" ).value( accounts.get(1).getPlatformName().toString() ) );
		
		verify( service, times(1) ).getAccounts();
		verifyNoMoreInteractions(service);
	};
	
	@Test
	public void testGetAccountsById() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/account/{id}";

		User newUser =  new User(1, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);
		SocialAccount account = new SocialAccount(id, "group2_facebook", "Facebook description", SocialAccount.Platform.FACEBOOK, newUser);
		
		when( service.getAccountById(id) ).thenReturn(account);
		
		mvc.perform( get(uri, id).with(user("group2").password("password")) )
				.andDo( print() )
				.andExpect( status().isOk() )
				.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE) )
				.andExpect( jsonPath( "$.id" ).value( account.getId() ) )
				.andExpect( jsonPath( "$.accountName" ).value( account.getAccountName() ) )
				.andExpect( jsonPath( "$.description" ).value( account.getDescription() ) )
				.andExpect( jsonPath( "$.platformName" ).value( account.getPlatformName().toString() ) );
		
		verify( service, times(1) ).getAccountById(id);
		verifyNoMoreInteractions(service);
	};
//	
	@Test
	public void testGetAccountsByIdNotFound() throws Exception {
		
		String uri = STARTING_URI + "/account/{id}";
		User newUser =  new User(1, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);
		int id = 1;
		
		when( service.getAccountById(id) ).thenThrow( new ResourceNotFoundException("Account was not found"));
		
		mvc.perform( get(uri, id).with(user("group2").password("password")) )
				.andDo( print() )
				.andExpect( status().isNotFound() );
		
		verify( service, times(1) ).getAccountById(id);
		verifyNoMoreInteractions(service);
	};
	
	@Test
	public void testCreateAccount() throws Exception {
		
		String uri = STARTING_URI + "/account";
		
		User newUser =  new User(1, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);
		SocialAccount account = new SocialAccount(1, "group2_facebook", "Facebook description", SocialAccount.Platform.FACEBOOK, newUser);
		String bodyText = "{\"id\" : " + account.getId() 
				+ ", \"accountName\" : \"" + account.getAccountName() + "\""
				+ ", \"platformName\" : \"" + account.getPlatformName() + "\""
				+ ", \"description\" : \"" + account.getDescription() + "\""
				+ ", \"user\" : {\"id\":" + newUser.getId() + "}}";
		
		when( service.createAccount( Mockito.any(SocialAccount.class) ) ).thenReturn(account);
		
		mvc.perform( post(uri)
					.with(user("group2").password("password"))
					.content(asJsonString(account))
					.contentType( MediaType.APPLICATION_JSON_VALUE) )
			.andDo( print() )
			.andExpect( status().isCreated() )
			.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE));
		
		verify( service, times(1) ).createAccount(Mockito.any(SocialAccount.class));
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser(username = "group2", roles = "USER")
	public void testCreateAccountSameUserAndPlatform() throws Exception {
		
		String uri = STARTING_URI + "/account";
		
		when( service.createAccount( Mockito.any(SocialAccount.class) ) ).thenThrow(new SameUserAndPlatformException() );
	
		mvc.perform( post(uri))
			.andDo( print() )
			.andExpect( status().isConflict() );

		verify( service, times(1) ).createAccount(Mockito.any(SocialAccount.class));
		verifyNoMoreInteractions(service);
	}
	
	
	@Test
	public void testUpdateAccount() throws Exception {
		
		String uri = STARTING_URI + "/account";
		
		User newUser =  new User(1, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);
		SocialAccount account = new SocialAccount(1, "group2_facebook", "Facebook description", SocialAccount.Platform.FACEBOOK, newUser);
		
		when( service.updateAccount( Mockito.any(SocialAccount.class) ) ).thenReturn(account);
		
		mvc.perform( put(uri).with(user("group2").password("password"))
					.contentType( MediaType.APPLICATION_JSON_VALUE) )
			.andDo( print() )
			.andExpect( status().isCreated() )
			.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE));
		
		verify( service, times(1) ).updateAccount(Mockito.any(SocialAccount.class));
		verifyNoMoreInteractions(service);
	}
//	
	@Test
	public void testUpdateAccountSameUserAndPlatform() throws Exception {
		
		String uri = STARTING_URI + "/account";
		
		when( service.updateAccount( Mockito.any(SocialAccount.class) ) ).thenThrow(new SameUserAndPlatformException() );
	
		mvc.perform( put(uri).with(user("group2").password("password")) )
			.andDo( print() )
			.andExpect( status().isConflict() );

		verify( service, times(1) ).updateAccount(Mockito.any(SocialAccount.class));
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testUpdateAccountNotFound() throws Exception {
		
		String uri = STARTING_URI + "/account";
		
		when( service.updateAccount( Mockito.any(SocialAccount.class) ) ).thenThrow(new ResourceNotFoundException("Account was not found") );
	
		mvc.perform( put(uri).with(user("group2").password("password")) )
			.andDo( print() )
			.andExpect( status().isNotFound() );

		verify( service, times(1) ).updateAccount(Mockito.any(SocialAccount.class));
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testDeleteAccount() throws Exception {
		
		String uri = STARTING_URI + "/account/{id}";
		
		int id = 1;
		User newUser =  new User(1, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);
		SocialAccount account = new SocialAccount(id, "group2_facebook", "Facebook description", SocialAccount.Platform.FACEBOOK, newUser);
		
		when(service.deleteAccount(id)).thenReturn(true);

		mvc.perform( delete(uri, id).with(user("group2").password("password")) )
				.andDo( print() )
				.andExpect( status().isOk() );
	    
		verify( service, times(1) ).deleteAccount(id);
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testDeleteAccountNotfound() throws Exception {
		
		String uri = STARTING_URI + "/account/{id}";
		
		int id = 1;
		
		when(service.deleteAccount(id)).thenThrow( new ResourceNotFoundException("Account was not found") );
		
		mvc.perform( delete(uri, id).with(user("group2").password("password")) )
			.andDo( print() )
			.andExpect( status().isNotFound() );

		verify( service, times(1) ).deleteAccount(id);
		verifyNoMoreInteractions(service);
	}
	
	// converts any object to a JSON string
	
	public static String asJsonString(final Object obj) {
		
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
}
