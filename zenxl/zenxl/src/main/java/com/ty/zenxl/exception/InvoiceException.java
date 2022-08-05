package com.ty.zenxl.exception;

/**
 * Raised only if {@code ImportInvoice} or {@code ExportInvoice} is not found.
 * 
 * Handled in {@code ZenxlExceptionHandler} class.
 * 
 * @author Abhishek
 * @author Indrajit
 * @version 1.0
 */
public class InvoiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvoiceException(String message) {
		super(message);
	}

}
