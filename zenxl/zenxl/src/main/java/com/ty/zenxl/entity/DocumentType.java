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
 * Defines the DocumentType table created in the database with the mentioned
 * fields.
 * 
 * @author Abhishek
 * @version 1.0
 *
 */

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document_type")
public class DocumentType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "document_type_id", unique = true, nullable = false, precision = 10)
	private Integer documentTypeId;
	@Column(name = "document_type_name", length = 15)
	private String documentTypeName;

}
