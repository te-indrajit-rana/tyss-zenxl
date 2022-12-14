package com.ty.zenxl.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the standard error format used throughout the application.
 * 
 * @author Indrajit
 * @author Abhishek
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_DEFAULT)
public class ZenxlErrorMessage {

	private Boolean isError;
	private String message;

}
