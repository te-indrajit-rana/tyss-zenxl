package com.ty.zenxl.exception;

/**
 * Raised only if {@code InspectionTypeDetails} is not found with the
 * mentioned inspection type.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Indrajit
 * @version 1.0
 */

public class InspectionTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InspectionTypeException(String message) {
		super(message);
	}
}
