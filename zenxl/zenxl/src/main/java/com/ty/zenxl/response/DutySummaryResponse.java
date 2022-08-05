package com.ty.zenxl.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response DTO for {@code DutySummary}
 * 
 * Ignores null valued properties.
 * 
 * @author Indrajit
 * @author Abhishek
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_DEFAULT)
public class DutySummaryResponse {

	private BigDecimal bcd;
	private BigDecimal acd;
	private BigDecimal sws;
	private BigDecimal nccd;
	private BigDecimal add;
	private BigDecimal cvd;
	private BigDecimal igst;
	private BigDecimal cess;
	private BigDecimal totalDuty;
	private BigDecimal assessedvalue;
	private BigDecimal iNT;
	private BigDecimal pnlty;
	private BigDecimal fine;
	private BigDecimal totalDutyAmount;

}
