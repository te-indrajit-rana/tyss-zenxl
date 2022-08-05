package com.ty.zenxl.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response DTO for {@code ItemDetails}.
 * 
 * Ignores null valued properties.
 * 
 * @author Abhishek
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_DEFAULT)
public class ItemResponse {

	private Long serialNumber;

	private String manufacturerName;

	private Long partNumber;

	private Integer quantity;

	private String description;

	private String unitOfMeasure;

	private Double unitPrice;

	private String countryOfOrigin;

	private Set<CodeResponse> codeList;

	private Set<CertificateResponse> certificateList;

}
