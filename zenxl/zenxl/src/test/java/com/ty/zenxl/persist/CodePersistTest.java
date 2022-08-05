package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.CodeTypeDetails;
import com.ty.zenxl.repository.CodeTypeRepository;

@DataJpaTest
class CodePersistTest {

	@Autowired
	private CodeTypeRepository codeTypeRepository;

	CodeTypeDetails codeType = CodeTypeDetails.builder().codeType("TEST1").build();

	@Test
	@DisplayName("Should Fetch CodeType Successfully")
	void shouldFetchCodeTypeSuccessfully(){

		CodeTypeDetails savedCodeType = codeTypeRepository.save(codeType);
		assertTrue(codeTypeRepository.existsByCodeType(savedCodeType.getCodeType()));
		assertThat(savedCodeType.getCodeType()).isEqualTo("TEST1");
	}

	@Test
	@DisplayName("Should Persist CodeType Successfully")
	void shouldPersistCodeTypeSuccessfully() {

		codeTypeRepository.save(codeType);
		assertTrue(codeTypeRepository.existsByCodeType("TEST1"));
	}

	@Test
	@DisplayName("Should Update CodeType Successfully")
	void shouldUpdateCodeSuccessfully() {

		CodeTypeDetails savedCodeType = codeTypeRepository.save(codeType);
		String intialCodeType = savedCodeType.getCodeType();

		savedCodeType.setCodeType("TEST123");
		CodeTypeDetails updatedCodeType = codeTypeRepository.save(savedCodeType);
		String updatedCodeTypeMessage = updatedCodeType.getCodeType();
		assertNotEquals(intialCodeType,updatedCodeTypeMessage);
	}

	@Test
	@DisplayName("Should Delete CodeType Successfully")
	void shouldDeleteCodeTypeSuccessfully() {

		CodeTypeDetails savedCodeType = codeTypeRepository.save(codeType);
		codeTypeRepository.deleteById(codeType.getCodeTypeId());
		assertFalse(codeTypeRepository.existsByCodeType(savedCodeType.getCodeType()));
	}

}
