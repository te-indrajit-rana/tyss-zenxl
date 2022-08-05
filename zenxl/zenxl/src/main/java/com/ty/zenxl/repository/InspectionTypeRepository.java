package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ty.zenxl.entity.InspectionTypeDetails;

/**
 * Interface to interact with db for {@code InspectionTypeDetails} entity class.
 * Extends the {@code JpaRepository} interface.
 * 
 * @author Indrajit
 * @verion 1.0
 */

public interface InspectionTypeRepository extends JpaRepository<InspectionTypeDetails, Integer> {

	Boolean existsByInspectionType(String inspectionType);

	Optional<InspectionTypeDetails> findByInspectionType(String inspectionType);

	Optional<InspectionTypeDetails> findByInspectionTypeId(Integer inspectionTypeId);

	@Modifying
	@Query("delete from InspectionTypeDetails c where c.inspectionTypeId=:inspectionTypeId")
	void deleteInspectionType(Integer inspectionTypeId);
}
