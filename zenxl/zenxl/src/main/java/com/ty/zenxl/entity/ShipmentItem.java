package com.ty.zenxl.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Defines the ShipmentItem table created in the database with the mentioned
 * fields.
 * 
 * @author Indrajit
 * @author Abhishek
 * @author Swathi
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
@Table(name = "shipment_item")
public class ShipmentItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shipment_item_id", unique = true, nullable = false, precision = 10)
	private Integer shipmentItemId;
	@Column(name = "internal_order_num", unique = true)
	private Long internalOrderNumber;
	@Column(name = "total_amount")
	private Double amount;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "itemDetailsId", referencedColumnName = "item_id")
	private ItemDetails itemDetails;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "shipmentItem")
	private Set<Inspection> inspectionList;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "import_invoice_id")
	private ImportInvoice importInvoice;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "export_invoice_id")
	private ExportInvoice exportInvoice;

}
