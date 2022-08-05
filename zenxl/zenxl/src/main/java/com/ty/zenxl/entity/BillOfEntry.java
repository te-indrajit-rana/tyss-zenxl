package com.ty.zenxl.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines the BillOfEntry table created in the database with the mentioned
 * fields.
 * 
 * @author Indrajit
 * @author Abhishek
 * @author Swathi
 * @version 1.0
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bill_of_entry")
public class BillOfEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bill_of_entry_id", unique = true, nullable = false, precision = 10)
	private Integer billOfEntryId;
	@Column(name = "invoice_num", unique = true)
	private Long invoiceNumber;
	@Column(name = "invoice_date")
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;
	@Column(name = "invoice_items")
	private Integer invoiceItems;
	@Column(name = "invoice_amount")
	private Double invoiceAmount;
	@Column(length = 10)
	private String currency;
	@Column(name = "exchange_inr")
	private BigDecimal exchange;
	@Column(name = "port_code", length = 25)
	private String portCode;
	@Column(name = "boe_number")
	private Long boeNumber;
	@Column(name = "boe_date")
	@Temporal(TemporalType.DATE)
	private Date boeDate;
	@Column(name = "gross_weight")
	private Double grossWeight;
	@Column(name = "custom_agent", length = 25)
	private String cHAgent;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "import_invoice", referencedColumnName = "import_invoice_id")
	private ImportInvoice importInvoice;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "export_invoice", referencedColumnName = "export_invoice_id")
	private ExportInvoice exportInvoice;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "duty", referencedColumnName = "duty_summary_id")
	private DutySummary duty;

}
