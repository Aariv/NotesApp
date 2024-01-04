package com.speer.notesapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.speer.notesapp.dto.JwtResponse;
import com.speer.notesapp.dto.LoginDto;
import com.speer.notesapp.dto.MessageResponse;
import com.speer.notesapp.dto.SignUpDto;
import com.speer.notesapp.model.ERole;
import com.speer.notesapp.model.Role;
import com.speer.notesapp.model.User;
import com.speer.notesapp.repository.RoleRepository;
import com.speer.notesapp.repository.UserRepository;
import com.speer.notesapp.security.JwtUtils;
import com.speer.notesapp.security.services.UserDetailsImpl;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	public MessageResponse registerUser(SignUpDto signUpDto) {
		if (userRepository.existsByUsername(signUpDto.getUsername())) {
			return new MessageResponse("Error: Username is already taken!");
		}

		// Create new user's account
		User user = new User(signUpDto.getUsername(), encoder.encode(signUpDto.getPassword()));

//		Set<String> strRoles = signUpDto.getRole();
		Set<Role> roles = new HashSet<>();
//
//		if (strRoles == null) {
//			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
//		} else {
//			strRoles.forEach(role -> {
//				switch (role) {
//				case "admin":
//					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(adminRole);
//
//					break;
//				default:
//					
//				}
//			});
//		}
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);

		return new MessageResponse("User registered successfully!");
	}

	public JwtResponse authenticateUser(LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
	}
}
