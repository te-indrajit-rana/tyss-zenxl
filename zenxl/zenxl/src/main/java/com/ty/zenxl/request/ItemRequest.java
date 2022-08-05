package com.ty.zenxl.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code ItemDetails}.
 * 
 * Contains bean validation properties, to validate the request object using
 * {@code @Valid} in {@code ZenxlItemController}, {@code ZenxlImportController}
 * and {@code ZenxlExportController}
 * 
 * @author Indrajit
 * @author Abhishek
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequest {

	@NotNull(message = "serialNumber cannot be null")
	private Long serialNumber;
	@NotNull(message = "manufacturerName cannot be null")
	@NotBlank(message = "manufacturerName cannot be blank")
	private String manufacturerName;
	@NotNull(message = "partNumber cannot be null")
	private Long partNumber;
	@Positive(message = "quantity cannot be a negative value")
	@NotNull(message = "quantity cannot be null")
	private Integer quantity;
	@NotNull(message = "description cannot be null")
	@NotBlank(message = "description cannot be blank")
	private String description;
	@NotNull(message = "unitOfMeasure cannot be null")
	@NotBlank(message = "unitOfMeasure cannot be blank")
	private String unitOfMeasure;
	@Positive(message = "unitPrice cannot be a negative value")
	@NotNull(message = "unitPrice cannot be null")
	private Double unitPrice;
	@NotNull(message = "countryOfOrigin cannot be null")
	@NotBlank(message = "countryOfOrigin cannot be blank")
	@Size(min = 3, max = 20, message = "countryOfOrigin size must lie between 3 and 20")
	private String countryOfOrigin;
	@Valid
	@NotNull(message = "codeRequestList cannot be null")
	private List<CodeRequest> codeRequestList;

}
