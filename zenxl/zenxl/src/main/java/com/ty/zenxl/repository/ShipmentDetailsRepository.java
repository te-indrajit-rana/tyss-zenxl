package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.ShipmentDetails;

/**
 * Interface to interact with db for {@code ShipmentDetails} entity class.
 * Extends the {@code JpaRepository} interface.
 * 
 * @author Indrajit
 * @author Abhishek
 * @author Swathi
 * @verion 1.0
 */
public interface ShipmentDetailsRepository extends JpaRepository<ShipmentDetails, Integer> {

	Optional<ShipmentDetails> findByWayBillNumber(String wayBillNumber);

	Boolean existsByWayBillNumber(String wayBillNumber);

}
