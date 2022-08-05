package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ty.zenxl.entity.CodeTypeDetails;

/**
 * Interface to interact with db for {@code CodeTypeDetails} entity class.
 * Extends the {@code JpaRepository} interface.
 * 
 * @author Indrajit
 * @verion 1.0
 */

public interface CodeTypeRepository extends JpaRepository<CodeTypeDetails, Integer> {

	Boolean existsByCodeType(String codeType);

	Optional<CodeTypeDetails> findByCodeType(String codeType);

	Optional<CodeTypeDetails> findByCodeTypeId(Integer codeTypeId);

	@Modifying
	@Query("delete from CodeTypeDetails c where c.codeTypeId=:codeTypeId")
	void deleteCodeType(Integer codeTypeId);
}
