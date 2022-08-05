package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.DocumentType;

/**
 * Interface to interact with db for {@code DocumentType} entity class. Extends
 * the {@code JpaRepository} interface.
 * 
 * @author Abhishek
 * @verion 1.0
 */
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {

	Optional<DocumentType> findByDocumentTypeName(String name);

	boolean existsByDocumentTypeName(String name);

}
