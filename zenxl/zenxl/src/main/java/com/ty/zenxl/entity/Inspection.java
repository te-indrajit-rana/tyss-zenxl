package com.ty.zenxl.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines the Inspection table created in the database with the mentioned
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
@Table(name = "inspection_details")
public class Inspection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inspection_id", unique = true, nullable = false, precision = 10)
	private Integer inspectionId;
	@Column(name = "inspection_value", length = 10)
	private String inspectionValue;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "inspection_inspectionType", joinColumns = @JoinColumn(name = "inspectionId", referencedColumnName = "inspection_id"), inverseJoinColumns = @JoinColumn(name = "inspectionTypeId", referencedColumnName = "inspection_type_id"))
	private InspectionTypeDetails inspectionType;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "shipment_item_id")
	private ShipmentItem shipmentItem;

}
