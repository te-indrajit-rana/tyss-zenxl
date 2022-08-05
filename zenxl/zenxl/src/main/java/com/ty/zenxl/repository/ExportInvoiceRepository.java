package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.ExportInvoice;

/**
 * Interface to interact with db for {@code ExportInvoice} entity class. Extends
 * the {@code JpaRepository} interface.
 * 
 * @author Indrajit
 * @author Abhishek
 * @verion 1.0
 */
public interface ExportInvoiceRepository extends JpaRepository<ExportInvoice, Integer> {

	Optional<ExportInvoice> findByInvoiceNumber(Long invoiceNumber);

	Boolean existsByInvoiceNumber(Long invoiceNumber);
}
