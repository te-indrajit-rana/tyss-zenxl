package com.ty.zenxl.exception;

/**
 * Raised only if {@code MultipartFile} is not found in the request part.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Abhishek
 * @version 1.0
 */
public class DocumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DocumentException(String message) {
		super(message);
	}

}
