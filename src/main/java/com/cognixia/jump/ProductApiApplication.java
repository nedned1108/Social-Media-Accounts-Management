package com.cognixia.jump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		//provides meta data on the API service
		info = @Info(
		title = "Social Media Accounts Management API", // title of the Documentation page
		version = "1.0",	// version of your API
		description = "API that allows the user to manage their different social media accounts.",
		contact = @Contact(name = "Cognixia, PEP2 Team 2", email = "jumpspartans@cognixia.com", url = "https://www.collabera.com"),
		license = @License(name = "Next Big Idea License v1.0", url = "https://www.collabera.com/"),
		termsOfService = "https://www.collabera.com/" // must be a url
	),
externalDocs = @ExternalDocumentation(description = "More info on the Social Media API", url = "https://github.com/nedned1108/Social-Media-Accounts-Management/tree/kirstie")
		)
public class ProductApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApiApplication.class, args);
	}

}
