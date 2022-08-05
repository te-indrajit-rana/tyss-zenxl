package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.PasscodeDetails;

/**
 * Interface to interact with db for {@code PasscodeDetails} entity class.
 * Extends the {@code JpaRepository} interface.
 * 
 * @author Indrajit
 * @verion 1.0
 */

public interface PasscodeRepository extends JpaRepository<PasscodeDetails, Integer> {

	Optional<PasscodeDetails> findByEmail(String email);

}
