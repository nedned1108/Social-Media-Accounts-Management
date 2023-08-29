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
			info = @Info(
						title = "Social Account Managment API",
						version = "1.0",
						description = "API that allows you to get, create, update and delete social media accounts.",
						contact = @Contact(name = "Ned Nguyen, Kayanna Laster, Kirstie Goggans", email = "jumpgroup2@cognixia.com", url = "https://github.com/nedned1108/Social-Media-Accounts-Management"),
						license = @License(name = "Social Acocunt License v1.0", url = "https://github.com/nedned1108/Social-Media-Accounts-Management"),
						termsOfService = "https://github.com/nedned1108/Social-Media-Accounts-Management"
					),
			externalDocs = @ExternalDocumentation(description = "More info on the Social Account API", url = "https://github.com/nedned1108/Social-Media-Accounts-Management")
)
public class SocialAccountApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialAccountApiApplication.class, args);
	}

}
