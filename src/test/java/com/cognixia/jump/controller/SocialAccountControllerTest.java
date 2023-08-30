package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.SocialAccount;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.SocialAccountRepository;
import com.cognixia.jump.service.SocialAccountService;


@WebMvcTest(SocialAccountController.class)
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
		
		verifyNoMoreInteractions( controller );
	};
	
	@Test
	public void testGetAccount() throws Exception {
		
		String uri = STARTING_URI + "/account/{id}";

		int id = 1;
		User newUser =  new User(id, "group2", "password", "Cognixia", User.Role.ROLE_USER,"bio example", null);
		SocialAccount account = new SocialAccount(1, "group2_facebook", "Facebook description", SocialAccount.Platform.FACEBOOK, newUser);
		
		when( repo.findById(id) ).thenReturn(Optional.of(account));
		
		ResponseEntity<?> response = controller.getAccountsById(id);
		
		mvc.perform( get(uri) )
				.andDo( print() )
				.andExpect( status().isOk() )
				.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE) )
				.andExpect( jsonPath( "$[0].id" ).value( account.getId() ) )
				.andExpect( jsonPath( "$[0].accountName" ).value( account.getAccountName() ) )
				.andExpect( jsonPath( "$[0].description" ).value( account.getDescription() ) )
				.andExpect( jsonPath( "$[0].platformName" ).value( account.getPlatform() ) )
				.andExpect( jsonPath( "$[0].user" ).value( account.getUser() ) );
		
		verify( repo, times(1) ).findById(id);
		verifyNoMoreInteractions(repo);
	};
	
	
	
	
	
	
	
	
	
}
