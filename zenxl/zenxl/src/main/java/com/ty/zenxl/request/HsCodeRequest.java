package com.ty.zenxl.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code HsCode}
 * 
 * Contains bean validation properties, to validate the request object using
 * {@code @Valid} in {@code ZenxlUtilityController}
 * 
 * @author Indrajit
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HsCodeRequest {

	@NotNull(message = "hsCodeType must not be null.")
	@NotBlank(message = "hsCodeType name must not be blank.")
	private String hsCodeType;
}
