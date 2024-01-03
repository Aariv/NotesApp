package com.speer.notesapp.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
	}

	public ResourceNotFoundException(String table, String field, Long id) {
		super();
	}
	
	public ResourceNotFoundException(String table, String field, String id) {
		super();
	}
}
