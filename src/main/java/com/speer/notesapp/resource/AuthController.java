package com.speer.notesapp.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speer.notesapp.dto.JwtResponse;
import com.speer.notesapp.dto.LoginDto;
import com.speer.notesapp.dto.SignUpDto;
import com.speer.notesapp.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "REST APIs for Authentication", description = "REST APIs in Speer to signup and login")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Operation(summary = "SignUp REST API", description = "REST API to register new user")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "HTTP Status CREATED") })
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
		authService.registerUser(signUpDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("User is successfully registered.");
	}

	@Operation(summary = "Login REST API", description = "REST API to login to the system using username and password")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status Ok") })
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
		JwtResponse jwt = authService.authenticateUser(loginDto);
		return ResponseEntity.ok(jwt);
	}
}
