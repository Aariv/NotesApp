package com.speer.notesapp.mapper;

import java.util.ArrayList;
import java.util.List;

import com.speer.notesapp.dto.NoteDto;
import com.speer.notesapp.model.Note;

public class NoteMapper {

	public static NoteDto mapToNoteDto(Note note, NoteDto noteDto) {
		noteDto.setTitle(note.getTitle());
		noteDto.setDescription(note.getDescription());
		noteDto.setDate(note.getDate());
		noteDto.setId(note.getId());
		return noteDto;
	}
	
	public static NoteDto mapToNoteDto(Note note) {
		return mapToNoteDto(note, new NoteDto());
	}
	
	public static List<NoteDto> mapToNoteDtos(List<Note> notes) {
		List<NoteDto> noteDtos = new ArrayList<>();
		for(Note note: notes) {
			noteDtos.add(mapToNoteDto(note, new NoteDto()));
		}
		return noteDtos;
	}

	public static Note mapToNote(NoteDto noteDto, Note note) {
		note.setTitle(noteDto.getTitle());
		note.setDescription(noteDto.getDescription());
		note.setDate(noteDto.getDate());
		note.setId(noteDto.getId());
		return note;
	}
	
	public static Note mapToNote(NoteDto noteDto) {
		Note note = new Note();
		note.setTitle(noteDto.getTitle());
		note.setDescription(noteDto.getDescription());
		note.setDate(noteDto.getDate());
		note.setId(noteDto.getId());
		return note;
	}

}