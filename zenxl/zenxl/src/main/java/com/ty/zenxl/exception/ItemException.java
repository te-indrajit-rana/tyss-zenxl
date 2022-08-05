package com.ty.zenxl.exception;

/**
 * Raised only if {@code ItemDetails} is not found.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Abhishek
 * @version 1.0
 */
public class ItemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ItemException(String message) {
		super(message);
	}

}
