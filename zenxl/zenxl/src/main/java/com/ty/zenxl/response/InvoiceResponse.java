package com.ty.zenxl.response;

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
 * Represents the response DTO for {@code ImportInvoice} and {@code ExportInvoice}.
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
public class InvoiceResponse {

	private Long invoiceNumber;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date invoiceDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date shipmentDate;

	private String exporterName;

	private String shipperName;

	private String countryOfExport;

	private String importerName;

	private String consigneeName;

	private String countryOfImport;

	private String incotermType;

	private String modeOfTransport;

	private String shipVia;

	private String currency;

	private Integer packageNumber;

	private Double grossWeight;

	private String wayBillNumber;

	private String remarks;

	private Set<ShipmentItemResponse> shipmentItemList;

}
