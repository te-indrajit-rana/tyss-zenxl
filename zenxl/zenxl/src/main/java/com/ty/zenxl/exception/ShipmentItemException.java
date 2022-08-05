package com.ty.zenxl.exception;

/**
 * Raised only if {@code ShipmentItem} is not found.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Abhishek
 * @author Indrajit
 * @author Swathi
 * @version 1.0
 */
public class ShipmentItemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ShipmentItemException(String message) {
		super(message);
	}

}
