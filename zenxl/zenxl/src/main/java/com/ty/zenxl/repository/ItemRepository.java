package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.ItemDetails;

/**
 * Interface to interact with db for {@code ItemDetails} entity class. Extends
 * the {@code JpaRepository} interface.
 * 
 * @author Abhishek
 * @verion 1.0
 */
public interface ItemRepository extends JpaRepository<ItemDetails, Integer> {

	Optional<ItemDetails> findBySerialNumber(long serialNumber);

	Boolean existsBySerialNumber(long serialNumber);

}
