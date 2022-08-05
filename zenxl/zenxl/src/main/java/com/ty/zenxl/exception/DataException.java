package com.ty.zenxl.exception;

/**
 * Raised only if {@code String} type data is not found with the multipart
 * file.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Abhishek
 * @version 1.0
 */
public class DataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataException(String message) {
		super(message);
	}

}
