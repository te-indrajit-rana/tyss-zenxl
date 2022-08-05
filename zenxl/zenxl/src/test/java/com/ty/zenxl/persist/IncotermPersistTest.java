package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.IncotermTypeDetails;
import com.ty.zenxl.repository.IncotermTypeRepository;

@DataJpaTest
class IncotermPersistTest {

	@Autowired
	private IncotermTypeRepository incotermTypeRepository;

	IncotermTypeDetails incotermType = IncotermTypeDetails.builder().incotermType("TEST1").build();

	@Test
	@DisplayName("Should Fetch IncotermType Successfully")
	void shouldFetchIncotermTypeSuccessfully(){

		IncotermTypeDetails savedIncotermType = incotermTypeRepository.save(incotermType);
		assertTrue(incotermTypeRepository.existsByIncotermType(savedIncotermType.getIncotermType()));
		assertThat(savedIncotermType.getIncotermType()).isEqualTo("TEST1");
	}

	@Test
	@DisplayName("Should Persist IncotermType Successfully.")
	void shouldPersistIncotermTypeSuccessfully() {

		IncotermTypeDetails savedIncotermType = incotermTypeRepository.save(incotermType);
		assertThat(savedIncotermType).usingRecursiveComparison().ignoringFields("incotermTypeId").isEqualTo(incotermType);
		assertTrue(incotermTypeRepository.existsByIncotermType("TEST1"));
	}

	@Test
	@DisplayName("Should Update IncotermType Successfully")
	void shouldUpdateIncotermTypeSuccessfully(){

		IncotermTypeDetails savedIncotermType = incotermTypeRepository.save(incotermType);
		String intialIncotermType = savedIncotermType.getIncotermType();

		savedIncotermType.setIncotermType("TEST123");
		IncotermTypeDetails updatedIncotermType = incotermTypeRepository.save(savedIncotermType);
		String updatedIncotermTypeMessage = updatedIncotermType.getIncotermType();
		assertNotEquals(intialIncotermType,updatedIncotermTypeMessage);
	}

	@Test
	@DisplayName("Should Delete IncotermType Successfully")
	void shouldDeleteIncotermTypeSuccessfully(){

		IncotermTypeDetails savedIncotermType = incotermTypeRepository.save(incotermType);
		incotermTypeRepository.deleteById(incotermType.getIncotermTypeId());
		assertFalse(incotermTypeRepository.existsByIncotermType(savedIncotermType.getIncotermType()));
	}

}
