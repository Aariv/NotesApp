package com.speer.notesapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

	private String username;
	private String password;
	
	public LoginDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
