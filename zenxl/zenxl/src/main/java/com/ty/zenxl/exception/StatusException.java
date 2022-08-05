package com.ty.zenxl.exception;

/**
 * Raised only if {@code Status} is not found with the mentioned code type.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Indrajit
 * @version 1.0
 */

public class StatusException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StatusException(String message) {
		super(message);
	}
}
