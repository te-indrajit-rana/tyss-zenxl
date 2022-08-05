package com.ty.zenxl.request;

import java.util.Date;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for reading data to be updated along with
 * {@code MultipartFile}.
 * 
 * Contains bean validation properties, to validate the request object using
 * {@code @Valid} in {@code ZenxlItemController}, {@code ZenxlImportController}
 * and {@code ZenxlExportController}
 * 
 * @author Abhishek
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertificateUpdateRequest {

	@NotNull(message = "certificateNumber cannot be null")
	@NotBlank(message = "certificateNumber cannot be blank")
	private String certificateNumber;

	@NotNull(message = "expiryDate cannot be null")
	@FutureOrPresent(message = "expiryDate cannot be past date")
	private Date expiryDate;

}
