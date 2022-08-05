package com.ty.zenxl.exception;

/**
 * Raised only if {@code User} is not found.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Indrajit
 * @version 1.0
 */

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserException(String message) {
		super(message);
	}
}
