package com.ty.zenxl.exception;

/**
 * Raised only when {@code User} is not valid to be authenticated.
 *
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Indrajit
 * @version 1.0
 */

public class LoginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoginException(String message) {
		super(message);
	}
}
