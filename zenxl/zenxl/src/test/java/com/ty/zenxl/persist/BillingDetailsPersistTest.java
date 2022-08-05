package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.Address;
import com.ty.zenxl.entity.BillingDetails;
import com.ty.zenxl.repository.BillingDetailsRepository;

@DataJpaTest
class BillingDetailsPersistTest {

	@Autowired
	private BillingDetailsRepository billingDetailsRepository;
	
	BillingDetails billingDetails = BillingDetails
			.builder().billingName("test-billing").billingContactNumber((long) 1234567890)
			.billingEmail("billing@gmail.com").address(Address.builder().address1("blr1")
					.address2("blr2").city("blr").state("krntk").country("india").zipCode((long) 12345).build())
			.build();

	@Test
	@DisplayName("Should Fetch Billing Details Successfully")
	void shouldFetchBillingDetailsSuccessfully() {

		billingDetailsRepository.save(billingDetails);
		assertThat(billingDetailsRepository.findByBillingName("test-billing").get()).isNotNull();
	}

	@Test
	@DisplayName("Should Persist Billing Details Successfully")
	void shouldPersistBillingDetailsSuccessfully() {

		BillingDetails savedBillingDetails = billingDetailsRepository.save(billingDetails);
		assertThat(savedBillingDetails).isNotNull();
	}

	@Test
	@DisplayName("Should Update Billing Details Successfully")
	void shouldUpdateBillingDetailsSuccessfully() {
		BillingDetails savedBillingDetails = billingDetailsRepository.save(billingDetails);
		String initialBillingDetailsName = savedBillingDetails.getBillingName();

		BillingDetails fetchedBillingDetails = billingDetailsRepository.findByBillingName(savedBillingDetails.getBillingName()).get();
		fetchedBillingDetails.setBillingName("update-billing");

		BillingDetails updatedBillingDetails = billingDetailsRepository.save(fetchedBillingDetails);
		String updatedBillingName = updatedBillingDetails.getBillingName();

		assertThat(initialBillingDetailsName).isNotEqualTo(updatedBillingName);
	}

	@Test
	@DisplayName("Should Delete Billing Details Successfully")
	void shouldDeleteBillingDetailsSuccessfully() {

		BillingDetails savedBillingDetails = billingDetailsRepository.save(billingDetails);
		billingDetailsRepository.deleteById(savedBillingDetails.getBillingDetailsId());
		assertFalse(billingDetailsRepository.existsByBillingDetailsId(savedBillingDetails.getBillingDetailsId()));
	}
}
