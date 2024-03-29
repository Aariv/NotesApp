package com.speer.notesapp.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speer.notesapp.dto.NoteDto;
import com.speer.notesapp.dto.ShareNoteDto;
import com.speer.notesapp.service.NoteService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CRUD REST APIs for Notes", description = "CRUD REST APIs in Speer to CREATE, UPDATE, FETCH AND DELETE notes")
@RestController
@RequestMapping("/api/notes")
@PreAuthorize("hasRole('USER')")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@Operation(summary = "Current User's All Notes REST API", description = "REST API to fetch all the notes of the current user")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "HTTP Status CREATED") })
	@GetMapping
	@CircuitBreaker(name = "notesCircuitBreaker", fallbackMethod = "getNotes")
	public ResponseEntity<List<NoteDto>> getAllNotes() {
		return ResponseEntity.status(HttpStatus.OK).body(noteService.getAllNotes());
	}
	
	public ResponseEntity<List<NoteDto>> getNotes(Throwable throwable) {
		List<NoteDto> notes = new ArrayList<>();
        notes.add(new NoteDto(1L, "", "", new Date()));
        return ResponseEntity.status(HttpStatus.OK).body(notes);
    }
	
	@Operation(summary = "Fetch Note Details REST API", description = "REST API to fetch note details")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status Ok") })
	@GetMapping("/{id}")
	public ResponseEntity<NoteDto> getNoteById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(noteService.getNoteById(id));
	}

	@Operation(summary = "Create Note REST API", description = "REST API to create new Note")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "HTTP Status CREATED") })
	@PostMapping
	public ResponseEntity<?> createNote(@RequestHeader("Authorization") String token, @RequestBody NoteDto noteDto) {
		noteService.createNote(noteDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(noteDto);
	}

	@Operation(summary = "Update Note REST API", description = "REST API to update the existing Note")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status Ok") })
	@PutMapping("/{id}")
	public ResponseEntity<?> updateNote(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody NoteDto noteDto) {
		noteService.createNote(noteDto);
		return ResponseEntity.status(HttpStatus.OK).body(noteDto);
	}

	@Operation(summary = "Delete Note REST API", description = "REST API to delete the existing Note")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status Ok") })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNote(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		noteService.deleteNote(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@Operation(summary = "Share Note REST API", description = "REST API to share the note to one or many users")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status Ok") })
	@PostMapping("/{id}/share")
	public ResponseEntity<?> shareNote(@PathVariable Long id, @RequestBody ShareNoteDto shareNoteDto) {
		noteService.shareNote(id, shareNoteDto);
		return ResponseEntity.status(HttpStatus.OK).body("Notes are shared successfully");
	}
}
