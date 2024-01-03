package com.speer.notesapp;

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
				title = "Speer REST API Documentation",
				description = "Speer REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Ariv",
						email = "arivbits@gmail.com",
						url = "https://aariv.github.io"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://aariv.github.io"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "Speer REST API Documentation",
				url = "http://localhost:8080/swagger-ui.html"
		)
)
public class NotesappApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesappApplication.class, args);
	}

}
