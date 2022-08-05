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
 * Defines the ImportDocument table created in the database with the mentioned
 * fields.
 * 
 * @author Abhishek
 * @version 1.0
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "import_document")
public class ImportDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "import_document_id", unique = true, nullable = false, precision = 10)
	private Integer importDocumentId;
	@Column(name = "document_name", length = 45)
	private String documentName;
	@Column(name = "document_format", length = 45)
	private String documentFormat;
	@Column(name = "document_url")
	private String documentUrl;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "import_document_type", joinColumns = @JoinColumn(name = "importDocId", referencedColumnName = "import_document_id"), inverseJoinColumns = @JoinColumn(name = "documentTypeId", referencedColumnName = "document_type_id"))
	private DocumentType documentType;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ImportInvoice importInvoice;

}
