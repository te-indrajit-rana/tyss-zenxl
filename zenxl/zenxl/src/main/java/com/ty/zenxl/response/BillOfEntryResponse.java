package com.ty.zenxl.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response DTO for {@code BillOfEntry}
 * 
 * Ignores null valued properties.
 *  
 * @author Indrajit
 * @author Abhishek
 * @author Swathi
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_DEFAULT)
public class BillOfEntryResponse {

	private Long invoiceNumber;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date invoiceDate;

	private Integer invoiceItems;

	private Double invoiceAmount;

	private String currency;

	private BigDecimal exchange;

	private String portCode;

	private Long boeNumber;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date boeDate;

	private Double grossWeight;

	private String importerName;

	private String cHAgent;

	private DutySummaryResponse dutySummary;

	private Set<ShipmentItemResponse> shipmentItemList;

}
