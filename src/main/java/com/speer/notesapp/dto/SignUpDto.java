package com.speer.notesapp.dto;

import java.util.Set;

import lombok.Data;

@Data
public class SignUpDto {

	private String username;
	private String password;
	// It's optional
	private Set<String> role;
}
