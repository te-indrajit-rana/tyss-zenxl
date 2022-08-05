package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.ImportDocument;

/**
 * Interface to interact with db for {@code ImportDocument} entity class.
 * Extends the {@code JpaRepository} interface.
 * 
 * @author Abhishek
 * @verion 1.0
 */
public interface ImportDocumentRepository extends JpaRepository<ImportDocument, Integer> {

	Optional<ImportDocument> findByImportDocumentId(Integer id);

	boolean existsByImportDocumentId(Integer id);

}
