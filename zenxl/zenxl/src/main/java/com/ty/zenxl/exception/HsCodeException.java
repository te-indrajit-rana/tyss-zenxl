package com.ty.zenxl.exception;

/**
 * Raised only if {@code HsCode} is not found with the mentioned hsCode type.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Indrajit
 * @version 1.0
 */

public class HsCodeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HsCodeException(String message) {
		super(message);
	}
}
