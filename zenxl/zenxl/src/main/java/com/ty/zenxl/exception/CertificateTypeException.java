package com.ty.zenxl.exception;

/**
 * Raised only if {@code CertificateTypeDetails} is not found with the mentioned
 * certificate type.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Indrajit
 * @version 1.0
 */

public class CertificateTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CertificateTypeException(String message) {
		super(message);
	}
}
