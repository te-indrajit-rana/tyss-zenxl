package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ty.zenxl.entity.IncotermTypeDetails;

/**
 * Interface to interact with db for {@code IncotermTypeDetails} entity class.
 * Extends the {@code JpaRepository} interface.
 * 
 * @author Indrajit
 * @verion 1.0
 */

public interface IncotermTypeRepository extends JpaRepository<IncotermTypeDetails, Integer> {

	Boolean existsByIncotermType(String incotermType);

	Optional<IncotermTypeDetails> findByIncotermType(String incotermType);

	Optional<IncotermTypeDetails> findByIncotermTypeId(Integer incotermTypeId);

	@Modifying
	@Query("delete from IncotermTypeDetails c where c.incotermTypeId=:incotermTypeId")
	void deleteIncotermType(Integer incotermTypeId);
}
