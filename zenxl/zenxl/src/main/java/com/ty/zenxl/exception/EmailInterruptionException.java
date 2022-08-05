package com.ty.zenxl.exception;

/**
 * Raised only when an email is not sent to an user.
 *
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Indrajit
 * @version 1.0
 */

public class EmailInterruptionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailInterruptionException(String message) {
		super(message);
	}
}
