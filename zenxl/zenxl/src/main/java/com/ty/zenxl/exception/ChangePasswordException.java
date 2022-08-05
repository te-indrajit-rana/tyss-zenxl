package com.ty.zenxl.exception;

/**
 * Raised only when {@code PasscodeDetails} is not able to update in the database.
 *
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Indrajit
 * @version 1.0
 */

public class ChangePasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ChangePasswordException(String message) {
		super(message);
	}

}
