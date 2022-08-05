package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.ImportInvoice;

/**
 * Interface to interact with db for {@code ImportInvoice} entity class. Extends
 * the {@code JpaRepository} interface.
 * 
 * @author Swathi
 * @author Abhishek
 * @author Indrajit
 * @verion 1.0
 */
public interface ImportInvoiceRepository extends JpaRepository<ImportInvoice, Integer> {

	Optional<ImportInvoice> findByInvoiceNumber(Long invoiceNumber);

	Boolean existsByInvoiceNumber(Long invoiceNumber);
}
