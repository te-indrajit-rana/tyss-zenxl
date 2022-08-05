package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ty.zenxl.entity.CertificateTypeDetails;

/**
 * Interface to interact with db for {@code CertificateTypeDetails} entity
 * class. Extends the {@code JpaRepository} interface.
 * 
 * @author Indrajit
 * @verion 1.0
 */

public interface CertificateTypeRepository extends JpaRepository<CertificateTypeDetails, Integer> {

	Boolean existsByCertificateType(String certificateType);

	Optional<CertificateTypeDetails> findByCertificateType(String certificateType);

	Optional<CertificateTypeDetails> findByCertificateTypeId(Integer certificateTypeId);

	@Modifying
	@Query("delete from CertificateTypeDetails c where c.certificateTypeId=:certificateTypeId")
	void deleteCertificateType(Integer certificateTypeId);

}
