package com.ty.zenxl.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Defines the ExportInvoice table created in the database with the mentioned
 * fields.
 * 
 * @author Indrajit
 * @author Abhishek
 * @version 1.0
 *
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "export_invoice_details")
public class ExportInvoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "export_invoice_id", unique = true, nullable = false, precision = 10)
	private Integer invoiceId;
	@Column(length = 45)
	private String customerName;
	@Column(name = "invoice_num", unique = true)
	private Long invoiceNumber;
	@Column(name = "invoice_date")
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;
	@Column(name = "ship_date")
	@Temporal(TemporalType.DATE)
	private Date shipmentDate;
	@Column(length = 45)
	private String exportReference;
	@Column(length = 45)
	private String remarks;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "exporterId", referencedColumnName = "exporter_id")
	private ExporterDetails exporterDetails;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "importerId", referencedColumnName = "importer_id")
	private ImporterDetails importerDetails;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "shipmentDetailsId", referencedColumnName = "shipment_details_id")
	private ShipmentDetails shipmentDetails;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "exportInvoice")
	private Set<ExportDocument> exportDocumentList;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "exportInvoice")
	private Set<ShipmentItem> shipmentItemList;

}
