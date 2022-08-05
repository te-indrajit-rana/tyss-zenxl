package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.CertificateTypeDetails;
import com.ty.zenxl.repository.CertificateTypeRepository;

@DataJpaTest
class CertificatePersistTest {

	@Autowired
	private CertificateTypeRepository certificateTypeRepository;

	CertificateTypeDetails certificateType = CertificateTypeDetails.builder().certificateType("TEST1").build();

	@Test
	@DisplayName("Should Fetch CertificateType Successfully")
	void shouldFetchCertificateTypeSuccessfully() {

		CertificateTypeDetails savedCertificateType = certificateTypeRepository.save(certificateType);
		assertTrue(certificateTypeRepository.existsByCertificateType(savedCertificateType.getCertificateType()));
		assertThat(savedCertificateType.getCertificateType()).isEqualTo("TEST1");
	}

	@Test
	@DisplayName("Should Persist CertificateType Successfully")
	void shouldPersistCertificateTypeSuccessfully() {

		certificateTypeRepository.save(certificateType);
		assertTrue(certificateTypeRepository.existsByCertificateType("TEST1"));
	}

	@Test
	@DisplayName("Should Update CertificateType Successfully")
	void shouldUpdateCertificateTypeSuccessfully() {

		CertificateTypeDetails savedCertificateType = certificateTypeRepository.save(certificateType);
		String intialCertificateType = savedCertificateType.getCertificateType();

		savedCertificateType.setCertificateType("TEST123");
		CertificateTypeDetails updatedCertificateType = certificateTypeRepository.save(savedCertificateType);
		String updatedCertificateTypeMessage = updatedCertificateType.getCertificateType();
		assertNotEquals(intialCertificateType,updatedCertificateTypeMessage);
	}

	@Test
	@DisplayName("Should Delete CertificateType Successfully")
	void shouldDeleteCertificateTypeSuccessfully() {

		CertificateTypeDetails savedCertificateType = certificateTypeRepository.save(certificateType);
		certificateTypeRepository.deleteById(certificateType.getCertificateTypeId());
		assertFalse(certificateTypeRepository.existsByCertificateType(savedCertificateType.getCertificateType()));
	}

}
