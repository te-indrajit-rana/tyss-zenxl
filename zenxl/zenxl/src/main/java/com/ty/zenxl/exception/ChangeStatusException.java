package com.ty.zenxl.exception;

/**
 * Raised only when {@code Status} is not able to update in the database.
 *
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Indrajit
 * @version 1.0
 */

public class ChangeStatusException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ChangeStatusException(String message) {
		super(message);
	}

}
