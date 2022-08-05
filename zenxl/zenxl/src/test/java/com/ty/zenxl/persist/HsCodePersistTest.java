package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.HsCode;
import com.ty.zenxl.repository.HsCodeRepository;

@DataJpaTest
class HsCodePersistTest {

	@Autowired
	private HsCodeRepository hsCodeRepository;

	HsCode hsCode = HsCode.builder().hsCodeType("TEST1").build();

	@Test
	@DisplayName("Should Fetch HsCode Successfully")
	void shouldFetchHsCodeSuccessfully(){

		HsCode savedHsCode = hsCodeRepository.save(hsCode);
		assertTrue(hsCodeRepository.existsByHsCodeType(savedHsCode.getHsCodeType()));
		assertThat(savedHsCode.getHsCodeType()).isEqualTo("TEST1");
	}

	@Test
	@DisplayName("Should Persist HsCode Successfully")
	void shouldPersistHsCodeSuccessfully() {

		hsCodeRepository.save(hsCode);
		assertTrue(hsCodeRepository.existsByHsCodeType("TEST1"));
	}

	@Test
	@DisplayName("Should Update HsCode Successfully")
	void shouldUpdateHsCodeSuccessfully() {

		HsCode savedHsCode = hsCodeRepository.save(hsCode);
		String intialHsCodeType = savedHsCode.getHsCodeType();

		savedHsCode.setHsCodeType("TEST123");
		HsCode updatedHsCode = hsCodeRepository.save(savedHsCode);
		String updatedHsCodeType = updatedHsCode.getHsCodeType();
		assertNotEquals(intialHsCodeType,updatedHsCodeType);
	}

	@Test
	@DisplayName("Should Delete HsCode Successfully")
	void shouldDeleteHsCodeSuccessfully() {

		HsCode savedHsCode = hsCodeRepository.save(hsCode);
		hsCodeRepository.deleteById(hsCode.getHsCodeId());
		assertFalse(hsCodeRepository.existsByHsCodeType(savedHsCode.getHsCodeType()));
	}

}
