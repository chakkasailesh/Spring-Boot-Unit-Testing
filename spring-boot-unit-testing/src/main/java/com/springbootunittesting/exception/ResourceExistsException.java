package com.springbootunittesting.exception;

public class ResourceExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;

	public ResourceExistsException(String message) {
		super();
		this.message = message;
	}

}
