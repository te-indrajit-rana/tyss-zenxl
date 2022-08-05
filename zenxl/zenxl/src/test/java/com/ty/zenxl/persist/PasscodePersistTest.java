package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.PasscodeDetails;
import com.ty.zenxl.repository.PasscodeRepository;

@DataJpaTest
class PasscodePersistTest {

	@Autowired
	private PasscodeRepository passcodeRepository;

	PasscodeDetails passcode1 = PasscodeDetails.builder().id(1).email("test1@gmail.com").passcode(111).build();

	@Test
	@DisplayName("Should Fetch Passcode Successfully")
	void shouldFetchPasscodeSuccessfully(){

		PasscodeDetails passcode = passcodeRepository.save(passcode1);

		assertThat(passcode.getEmail()).isEqualTo("test1@gmail.com");
		assertTrue(passcodeRepository.existsById(passcode.getId()));
	}

	@Test
	@DisplayName("Should Persist Passcode Successfully")
	void shouldPersistPasscodeSuccessfully(){

		passcodeRepository.save(passcode1);
		assertThat(passcodeRepository.findByEmail("test1@gmail.com")).isNotEmpty();

	}

	@Test
	@DisplayName("Should Update Passcode Successfully")
	void shouldUpdatePasscodeSuccessfully(){

		PasscodeDetails savedPasscode = passcodeRepository.save(passcode1);
		int initialPasscode = savedPasscode.getPasscode();

		savedPasscode.setPasscode(123);
		PasscodeDetails updatedPasscode = passcodeRepository.save(savedPasscode);
		int updatedPasscodeNo = updatedPasscode.getPasscode();
		assertThat(initialPasscode).isNotEqualTo(updatedPasscodeNo);
	}

}
