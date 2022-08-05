package com.ty.zenxl.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response DTO for {@code ShipmentItem}.
 * 
 * Ignores null valued properties.
 *  
 * @author Indrajit
 * @author Abhishek
 * @author Swathi
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_DEFAULT)
public class ShipmentItemResponse {

	private Long partNumber;

	private Long internalOrderNumber;

	private Long serialNumber;

	private String manufacturerName;

	private String description;

	private String countryOfOrigin;

	private Integer quantity;

	private String unitOfMeasure;

	private Double unitPrice;

	private Double amount;

	private Set<CodeResponse> codeList;

	private Set<CertificateResponse> certificateList;

	private Set<InspectionResponse> inspectionList;

}
