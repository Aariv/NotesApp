package com.speer.notesapp.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {

	private Long id;

	private String title;

	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;

}
