package com.speer.notesapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speer.notesapp.dto.NoteDto;
import com.speer.notesapp.dto.ShareNoteDto;
import com.speer.notesapp.exception.ResourceNotFoundException;
import com.speer.notesapp.mapper.NoteMapper;
import com.speer.notesapp.model.Note;
import com.speer.notesapp.model.User;
import com.speer.notesapp.repository.NoteRepository;
import com.speer.notesapp.repository.UserRepository;
import com.speer.notesapp.security.JwtUtils;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

/**
 * We can add REDIS/Memcached so that the for frequent request we can serve from Cache instead from DB.
 * 
 * @author Ariv
 *
 */
@Service
@RateLimiter(name = "simpleRateLimit")
public class NoteService {
	
	private static final Logger logger = LoggerFactory.getLogger(NoteService.class);
	
	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtils jwtUtils;

//	@PersistenceContext
//	protected EntityManager entityManager;

	/**
	 * 
	 * @return
	 */
	@RateLimiter(name = "simpleRateLimit", fallbackMethod = "getAllNotesFallbackMethod")
	public List<NoteDto> getAllNotes() {
		List<Note> notes = noteRepository.findByUserUsername(jwtUtils.getCurrentUserName());
		List<Note> sharedNotes = noteRepository.findAllNotes(jwtUtils.getCurrentUserName());
		notes.addAll(sharedNotes);
        return NoteMapper.mapToNoteDtos(notes);
	}

	private List<NoteDto> getAllNotesFallbackMethod(RequestNotPermitted requestNotPermitted) {
		logger.info("Fallback method called.");
		logger.info("RequestNotPermitted exception message: {}", requestNotPermitted.getMessage());
        return new ArrayList<NoteDto>();
    }
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public NoteDto getNoteById(Long id) {
		Optional<Note> note = noteRepository.findByUserUsernameAndId(jwtUtils.getCurrentUserName(), id);
		if(!note.isPresent()) {
			throw new ResourceNotFoundException();
		}
		return NoteMapper.mapToNoteDto(note.get(), new NoteDto());
	}

	/**
	 * 
	 * @param noteDto
	 */
	public void createNote(NoteDto noteDto) {
		Note note = NoteMapper.mapToNote(noteDto);
		note.setUser(jwtUtils.getCurrentUser());
		noteRepository.save(note);
	}

	/**
	 * 
	 * @param id
	 * @param noteDto
	 */
	public void updateNote(Long id, NoteDto noteDto) {
		noteDto.setId(id);
		Note note = NoteMapper.mapToNote(noteDto);
		note.setUser(jwtUtils.getCurrentUser());
		noteRepository.save(note);
	}

	/**
	 * 
	 * @param id
	 */
	public void deleteNote(Long id) {
		Optional<Note> note = noteRepository.findByUserUsernameAndId(jwtUtils.getCurrentUserName(), id);
		if(!note.isPresent()) {
			throw new ResourceNotFoundException();
		}
		noteRepository.deleteById(id);
	}

	/**
	 * 
	 * @param id
	 * @param shareNoteRequest
	 */
	public void shareNote(Long id, ShareNoteDto shareNoteRequest) {
		// Get the note to be shared
		Note note = noteRepository.findByUserUsernameAndId(jwtUtils.getCurrentUserName(), id).orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));

		// Find the user to share the note with
		shareNoteRequest.getSharedWithUsername().forEach(user -> {
			User sharedWith = userRepository.findByUsername(user)
					.orElseThrow(() -> new ResourceNotFoundException("User", "username", user));

			// Add the user to the sharedWith list of the note
			note.addSharedWith(sharedWith);
			noteRepository.save(note);
		});
	}

	/**
	 * TODO: 
	 * 
	 * 1. We can use elastic-search to index all the notes and have query from there.
	 * 2. We can also use Hibernate-Search for the better performance.
	 * 
	 * 
	 * @param searcText
	 * @return
	 */
	public List<NoteDto> searchNotes(String searchText) {
		List<NoteDto> result = new ArrayList<>();
		List<Note> searchResults = noteRepository.searchNotes(searchText);
		result.addAll(NoteMapper.mapToNoteDtos(searchResults));
		return result;
	}
	
//	public List<NoteDto> elasticSearch(String searcText) {
//
//		SearchSession searchSession = Search.session(entityManager);
//
//		SearchResult<Note> result = searchSession.search(Note.class).extension(ElasticsearchExtension.get())
//				.where(f -> f.simpleQueryString().fields("title","description").matching(searcText)).fetch(1000);
//
//		List<Note> results = result.hits();
////		long totalHitCount = result.total().hitCount();
////		log.info("Total hit count:" + totalHitCount);
//		List<NoteDto> responseResult = new ArrayList<>();
//		responseResult.addAll(NoteMapper.mapToNoteDtos(results));
//		return responseResult;
//	}
}
