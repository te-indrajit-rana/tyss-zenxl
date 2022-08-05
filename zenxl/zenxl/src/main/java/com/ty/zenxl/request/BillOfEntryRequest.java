package com.ty.zenxl.request;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code BillOfEntry}.
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillOfEntryRequest {

	private BigDecimal exchange;
	@NotNull(message = "portCode cannot be null")
	@NotBlank(message = "portCode cannot be blank")
	private String portCode;
	@NotNull(message = "boeNumber cannot be null")
	private Long boeNumber;
	@NotNull(message = "boeDate cannot be null")
	@FutureOrPresent(message = "boeDate cannot be a past date")
	private Date boeDate;
	@NotNull(message = "cHAgent cannot be null")
	@NotBlank(message = "cHAgent cannot be blank")
	private String cHAgent;
	private BigDecimal bcd;
	private BigDecimal acd;
	private BigDecimal sws;
	private BigDecimal nccd;
	private BigDecimal add;
	private BigDecimal cvd;
	private BigDecimal igst;
	private BigDecimal cess;
	private BigDecimal assessedvalue;
	private BigDecimal totalDuty;
	private BigDecimal iNT;
	private BigDecimal pnlty;
	private BigDecimal fine;
	private BigDecimal totalDutyAmount;

}
