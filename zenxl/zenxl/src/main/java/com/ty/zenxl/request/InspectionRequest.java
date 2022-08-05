package com.ty.zenxl.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code Inspection}.
 * 
 * Contains bean validation properties, to validate the request object using
 * {@code @Valid} in {@code ZenxlImportController} and
 * {@code ZenxlExportController}
 * 
 * @author Indrajit
 * @author Abhishek
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InspectionRequest {

	@NotNull(message = "inspectionType cannot be null")
	@NotBlank(message = "inspectionType cannot be blank")
	private String inspectionType;
	@NotNull(message = "inspectionValue cannot be null")
	@NotBlank(message = "inspectionValue cannot be blank")
	private String inspectionValue;

}
