package com.speer.notesapp.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.speer.notesapp.dto.NoteDto;
import com.speer.notesapp.service.NoteService;

@RestController
@RequestMapping("/api/search")
@PreAuthorize("hasRole('USER')")
public class SearchController {

	@Autowired
	private NoteService noteService;

	@GetMapping
	public ResponseEntity<List<NoteDto>> searchNotes(@RequestParam String q) {
		return ResponseEntity.ok(noteService.searchNotes(q));
	}
}
