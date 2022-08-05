package com.ty.zenxl.exception;

/**
 * Raised only if {@code Customer} is not found.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Indrajit
 * @version 1.0
 */

public class CustomerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerException(String message) {
		super(message);
	}
}
