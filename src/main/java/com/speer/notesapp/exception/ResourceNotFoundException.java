package com.speer.notesapp.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String table, String field, Long id) {
		super("Resource not found from " + table +  field + id);
	}
	
	public ResourceNotFoundException(String table, String field, String id) {
		super("Resource not found from " + table +  field + id);
	}
}
