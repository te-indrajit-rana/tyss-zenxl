package com.ty.zenxl.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code ImportDocument} or
 * {@code ExportDocument}.
 * 
 * Contains bean validation properties, to validate the request object using
 * {@code @Valid} in {@code ZenxlImportController} and
 * {@code ZenxlExportController}
 * 
 * @author Abhishek
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadRequest {

	@NotNull(message = "documentTypeName cannot be null")
	@NotBlank(message = "documentTypeName cannot be blank")
	private String documentTypeName;
	@NotNull(message = "invoiceNumber cannot be null")
	private Long invoiceNumber;

}
