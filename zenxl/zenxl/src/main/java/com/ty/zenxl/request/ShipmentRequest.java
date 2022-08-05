package com.ty.zenxl.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code ShipmentItem}.
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
public class ShipmentRequest {

	@NotNull(message = "internalOrderNumber cannot be null")
	private Long internalOrderNumber;
	@NotNull(message = "amount cannot be null")
	@Positive(message = "amount cannot be a negative value")
	private Double amount;
	@Valid
	@NotNull(message = "itemRequest cannot be null")
	private ItemRequest itemRequest;
	@Valid
	@NotNull(message = "inspectionList cannot be null")
	private List<InspectionRequest> inspectionList;

}
