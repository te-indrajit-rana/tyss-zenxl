package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.ShipmentItem;

/**
 * Interface to interact with db for {@code ShipmentItem} entity class. Extends
 * the {@code JpaRepository} interface.
 * 
 * @author Abhishek
 * @author Indrajit
 * @author Swathi
 * @verion 1.0
 */
public interface ShipmentItemRepository extends JpaRepository<ShipmentItem, Integer> {

	Optional<ShipmentItem> findByInternalOrderNumber(Long internalOrderNumber);

	Boolean existsByInternalOrderNumber(Long internalOrderNumber);

}
