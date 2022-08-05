package com.ty.zenxl.exception;

/**
 * Raised only if {@code ShipmentDetails} is not found.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Abhishek
 * @author Indrajit
 * @author Swathi
 * @version 1.0
 */
public class ShipmentDetailsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ShipmentDetailsException(String message) {
		super(message);
	}

}
