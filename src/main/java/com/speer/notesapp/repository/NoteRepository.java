package com.speer.notesapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.speer.notesapp.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long>{

	List<Note> findByUserUsername(String username);
	
	Optional<Note> findByUserUsernameAndId(String username, Long id);
	
	@Query("SELECT n FROM Note n JOIN n.sharedWith s WHERE s.username LIKE %?1%")
	List<Note> findAllNotes(String username);

}
