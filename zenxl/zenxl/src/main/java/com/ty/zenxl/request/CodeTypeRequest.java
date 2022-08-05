package com.ty.zenxl.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code CodeTypeDetails}.
 * 
 * Contains bean validation properties, to validate the request object using
 * {@code @Valid} in {@code ZenxlUtilityController},
 * {@code ZenxlItemController}, {@code ZenxlImportController} and
 * {@code ZenxlExportController}
 * 
 * @author Indrajit
 * @author Abhishek
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeTypeRequest {

	@NotNull(message = "codeType must not be null.")
	@NotBlank(message = "codeType must not be blank.")
	private String codeType;

}
