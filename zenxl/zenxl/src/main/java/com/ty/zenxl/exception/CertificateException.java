package com.ty.zenxl.exception;

/**
 * Raised only if {@code Certificate} is not found.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Abhishek
 * @version 1.0
 */
public class CertificateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CertificateException(String message) {
		super(message);
	}

}
