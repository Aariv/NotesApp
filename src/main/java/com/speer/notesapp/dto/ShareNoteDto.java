package com.speer.notesapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class ShareNoteDto {
	
	// As the username is unique we can pass
	private List<String> sharedWithUsername;
	
}
