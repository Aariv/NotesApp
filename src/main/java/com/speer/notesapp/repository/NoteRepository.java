package com.speer.notesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.speer.notesapp.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long>{

}
