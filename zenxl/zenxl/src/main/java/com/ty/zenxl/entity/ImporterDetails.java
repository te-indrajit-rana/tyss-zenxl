package com.ty.zenxl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines the ImporterDetails table created in the database with the mentioned
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
@Table(name = "importer_details")
public class ImporterDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "importer_id", unique = true, nullable = false, precision = 10)
	private Integer importerId;
	@Column(name = "importer_name", length = 45)
	private String importerName;
	@Column(name = "consignee_name", length = 45)
	private String consigneeName;
	@Column(name = "country_of_import", length = 45)
	private String countryOfImport;

}
