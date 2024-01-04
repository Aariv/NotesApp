package com.speer.notesapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpDto {

	private String username;
	private String password;
	
	public SignUpDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
}
