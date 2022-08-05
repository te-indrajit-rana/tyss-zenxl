package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.ExportDocument;

/**
 * Interface to interact with db for {@code ExportDocument} entity class.
 * Extends the {@code JpaRepository} interface.
 * 
 * @author Abhishek
 * @verion 1.0
 */
public interface ExportDocumentRepository extends JpaRepository<ExportDocument, Integer> {

	Optional<ExportDocument> findByExportDocumentId(Integer id);

	boolean existsByExportDocumentId(Integer id);

}
