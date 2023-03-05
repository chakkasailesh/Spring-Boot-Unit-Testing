package com.springbootunittesting.exception;

public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;

	public ResourceNotFoundException(String message) {
		super();
		this.message = message;
	}

}
