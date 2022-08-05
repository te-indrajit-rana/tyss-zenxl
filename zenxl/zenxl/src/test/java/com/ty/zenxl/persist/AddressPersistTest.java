package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.Address;
import com.ty.zenxl.repository.AddressRepository;

@DataJpaTest
class AddressPersistTest {

	@Autowired
	private AddressRepository addressRepository;

	Address address = Address.builder().address1("blr1").address2("blr2").city("blr").state("krntk").country("india")
			.zipCode((long) 12345).build();

	@Test
	@DisplayName("Should Fetch Address Successfully")
	void shouldFetchAddressSuccessfully() {

		Address savedAddress = addressRepository.save(address);
		assertThat(savedAddress).isNotNull();
	}

	@Test
	@DisplayName("Should Persist Address Successfully")
	void shouldPersistAddressSuccessfully() {

		Address savedAddress = addressRepository.save(address);
		assertThat(addressRepository.findByAddressId(savedAddress.getAddressId())).isNotNull();
		
	}

	@Test
	@DisplayName("Should Update Address Successfully")
	void shouldUpdateAddressSuccessfully() {

		Address savedAddress = addressRepository.save(address);
		String initialCity = savedAddress.getCity();

		Address fetchedAddress = addressRepository.findByAddressId(savedAddress.getAddressId()).get();
		fetchedAddress.setCity("bbsr");

		Address updatedAddress = addressRepository.save(fetchedAddress);
		String updatedCity = updatedAddress.getCity();

		assertThat(initialCity).isNotEqualTo(updatedCity);
	}

	@Test
	@DisplayName("Should Delete Address Successfully")
	void shouldDeleteAddressSuccessfully() {

		Address savedAddress = addressRepository.save(address);
		addressRepository.deleteById(savedAddress.getAddressId());
		assertFalse(addressRepository.existsByAddressId(savedAddress.getAddressId()));
	}

}
