package com.speer.notesapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import com.speer.notesapp.dto.NoteDto;
import com.speer.notesapp.dto.ShareNoteDto;
import com.speer.notesapp.exception.ResourceNotFoundException;
import com.speer.notesapp.mapper.NoteMapper;
import com.speer.notesapp.model.Note;
import com.speer.notesapp.model.User;
import com.speer.notesapp.repository.ElasticNoteRepository;
import com.speer.notesapp.repository.NoteRepository;
import com.speer.notesapp.repository.UserRepository;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;

@Service
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private ElasticNoteRepository elasticNoteRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<NoteDto> getAllNotes() {
		List<Note> notes = noteRepository.findAll();
		return NoteMapper.mapToNoteDtos(notes);
	}

	public NoteDto getNoteById(Long id) {
		Note note = noteRepository.findById(id).get();
		return NoteMapper.mapToNoteDto(note, new NoteDto());
	}

	public void createNote(NoteDto noteDto) {
		Note note = NoteMapper.mapToNote(noteDto);
		noteRepository.save(note);
		elasticNoteRepository.save(note);
	}

	public void updateNote(Long id, NoteDto noteDto) {
		noteDto.setId(id);
		noteRepository.save(NoteMapper.mapToNote(noteDto));
	}

	public void deleteNote(Long id) {
		noteRepository.deleteById(id);
	}

	public void shareNote(Long id, ShareNoteDto shareNoteRequest) {
		// Get the note to be shared
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));

        // Find the user to share the note with
        shareNoteRequest.getSharedWithUsername().forEach(user -> {
        	User sharedWith = userRepository.findByUsername(user)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", user));

            // Add the user to the sharedWith list of the note
            note.addSharedWith(sharedWith);
            noteRepository.save(note);
        });
	}
	
	public List<Note> searchNotes(String query) {
		return null;
    }
}
