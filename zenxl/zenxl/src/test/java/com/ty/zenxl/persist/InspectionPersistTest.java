package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.InspectionTypeDetails;
import com.ty.zenxl.repository.InspectionTypeRepository;

@DataJpaTest
class InspectionPersistTest {

	@Autowired
	private InspectionTypeRepository inspectionTypeRepository;

	InspectionTypeDetails inspectionType = InspectionTypeDetails.builder().inspectionType("TEST1").build();

	@Test
	@DisplayName("Should Fetch InspectionType Successfully")
	void shouldFetchInspectionTypeSuccessfully() {

		InspectionTypeDetails savedInspectionType = inspectionTypeRepository.save(inspectionType);
		assertTrue(inspectionTypeRepository.existsByInspectionType(savedInspectionType.getInspectionType()));
		assertThat(savedInspectionType.getInspectionType()).isEqualTo("TEST1");
	}

	@Test
	@DisplayName("Should Persist InspectionType Successfully")
	void shouldPersistInspectionTypeSuccessfully() {

		inspectionTypeRepository.save(inspectionType);
		assertTrue(inspectionTypeRepository.existsByInspectionType("TEST1"));
	}

	@Test
	@DisplayName("Should Update InspectionType Successfully")
	void shouldUpdateInspectionTypeSuccessfully(){

		InspectionTypeDetails savedInspectionType = inspectionTypeRepository.save(inspectionType);
		String intialInspectionType = savedInspectionType.getInspectionType();

		savedInspectionType.setInspectionType("TEST123");
		InspectionTypeDetails updatedInspectionType = inspectionTypeRepository.save(savedInspectionType);
		String updatedInspectionTypeMessage = updatedInspectionType.getInspectionType();
		assertNotEquals(intialInspectionType,updatedInspectionTypeMessage);
	}

	@Test
	@DisplayName("Should Delete InspectionType Successfully")
	void shouldDeleteInspectionTypeSuccessfully() {

		InspectionTypeDetails savedInspectionType = inspectionTypeRepository.save(inspectionType);
		inspectionTypeRepository.deleteById(inspectionType.getInspectionTypeId());
		assertFalse(inspectionTypeRepository.existsByInspectionType(savedInspectionType.getInspectionType()));
	}

}
