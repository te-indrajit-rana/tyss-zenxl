package com.ty.zenxl.exception;

/**
 * Raised only if {@code BillOfEntry} is not found.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Abhishek
 * @author Indrajit
 * @author Swathi
 * @version 1.0
 */
public class BillOfEntryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BillOfEntryException(String message) {
		super(message);
	}
}
