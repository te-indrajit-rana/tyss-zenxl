package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.BillOfEntry;

/**
 * Interface to interact with db for {@code BillOfEntry} entity class. Extends
 * the {@code JpaRepository} interface.
 * 
 * @author Indrajit
 * @author Abhishek
 * @author Swathi
 * @verion 1.0
 */
public interface BillOfEntryRepository extends JpaRepository<BillOfEntry, Integer> {

	Optional<BillOfEntry> findByBillOfEntryId(Integer billOfEntryId);

	Optional<BillOfEntry> findByBoeNumber(Long boeNumber);

	Optional<BillOfEntry> findByInvoiceNumber(Long invoiceNumber);

	Boolean existsByBoeNumber(Long boeNumber);
}
