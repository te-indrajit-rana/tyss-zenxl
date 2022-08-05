package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.Status;
import com.ty.zenxl.repository.StatusRepository;

@DataJpaTest
class StatusPersistTest {

	@Autowired
	private StatusRepository statusRepository;
	
	Status status = Status.builder().statusName("APPROVED").description("For Approval Of Items To Import").statusCategory("IMPORT").build();

	@Test
	@DisplayName("Should Fetch Status Successfully")
	void shouldFetchStatusSuccessfully() {

		statusRepository.save(status);
		assertThat(statusRepository.findAllByStatusName("APPROVED").get().get(0)).isNotNull();
		assertThat(statusRepository.findByStatusNameAndStatusCategory("APPROVED","IMPORT").get()).isNotNull();
	}
	
	@Test
	@DisplayName("Should Persist Status Successfully")
	void shouldPersistStatusSuccessfully() {

		Status savedStatus = statusRepository.save(status);
		assertThat(savedStatus).isNotNull();
	}

	@Test
	@DisplayName("Should Update Status Successfully")
	void shouldUpdateStatusSuccessfully(){

		Status savedStatus = statusRepository.save(status);
		String intialStatusName = savedStatus.getStatusName();
		
		Status fetchedStatus = statusRepository.findByStatusNameAndStatusCategory(savedStatus.getStatusName(),savedStatus.getStatusCategory()).get();
		fetchedStatus.setStatusName("PENDING");
		
		Status updatedStatus = statusRepository.save(fetchedStatus);
		String updatedStatusName = updatedStatus.getStatusName();
		
		assertThat(intialStatusName).isNotEqualTo(updatedStatusName);
	}

	@Test
	@DisplayName("Should Delete Status Successfully")
	void shouldDeleteStatusSuccessfully(){

		Status savedStatus = statusRepository.save(status);
		statusRepository.delete(savedStatus);
		assertFalse(statusRepository.existsByStatusNameAndStatusCategory("APPROVED","IMPORT"));
	}

}
