package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.Address;
import com.ty.zenxl.entity.BillingDetails;
import com.ty.zenxl.entity.Customer;
import com.ty.zenxl.repository.CustomerRepository;

@DataJpaTest
class CustomerPersistTest {

	@Autowired
	private CustomerRepository customerRepository;

	Customer customer = Customer.builder().customerName("test").contactPerson("test_contact")
			.phoneNumber((long) 1234567890).email("test@gmail.com")
			.address(
					Address.builder().address1("blr1").address2("blr2").city("blr").state("krntk").country("india")
							.zipCode((long) 12345).build())
			.billingDetails(BillingDetails.builder().billingName("test-billing").billingContactNumber((long) 1234567890)
					.billingEmail("billing@gmail.com").address(Address.builder().address1("blr1").address2("blr2")
							.city("blr").state("krntk").country("india").zipCode((long) 12345).build())
					.build())
			.build();

	@Test
	@DisplayName("Should Fetch Customer Successfully")
	void shouldFetchCustomerSuccessfully() {

		customerRepository.save(customer);
		assertThat(customerRepository.findByCustomerName("test").get()).isNotNull();
	}

	@Test
	@DisplayName("Should Persist Customer Successfully")
	void shouldPersistCustomerSuccessfully() {

		Customer savedCustomer = customerRepository.save(customer);
		assertThat(savedCustomer).isNotNull();
	}

	@Test
	@DisplayName("Should Update Customer Successfully")
	void shouldUpdateCustomerSuccessfully() {
		Customer savedCustomer = customerRepository.save(customer);
		String intialEmail = savedCustomer.getEmail();

		Customer fetchedCustomer = customerRepository.findByCustomerId(savedCustomer.getCustomerId()).get();
		fetchedCustomer.setEmail("test123@gmail.com");

		Customer updatedCustomer = customerRepository.save(fetchedCustomer);
		String updatedEmail = updatedCustomer.getEmail();

		assertThat(intialEmail).isNotEqualTo(updatedEmail);
	}

	@Test
	@DisplayName("Should Delete Customer Successfully")
	void shouldDeleteCustomerSuccessfully() {

		Customer savedCustomer = customerRepository.save(customer);
		customerRepository.deleteById(savedCustomer.getCustomerId());
		assertFalse(customerRepository.existsByCustomerName(savedCustomer.getCustomerName()));
	}

}
