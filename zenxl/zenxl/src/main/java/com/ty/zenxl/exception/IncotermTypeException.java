package com.ty.zenxl.exception;

/**
 * Raised only if {@code IncotermTypeDetails} is not found with the mentioned
 * incoterm type.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Indrajit
 * @version 1.0
 */

public class IncotermTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IncotermTypeException(String message) {
		super(message);
	}
}
