package com.ty.zenxl.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response DTO for {@code Code}
 * 
 * Ignores null valued properties.
 *  
 * @author Indrajit
 * @author Abhishek
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_DEFAULT)
public class CodeResponse {

	private String codeType;
	private String codeValue;

}