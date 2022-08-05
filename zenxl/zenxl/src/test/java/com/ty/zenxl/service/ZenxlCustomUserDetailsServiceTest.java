package com.ty.zenxl.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ty.zenxl.entity.User;
import com.ty.zenxl.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ZenxlCustomUserDetailsServiceTest {

	@Mock
	private UserRepository userRepository;

	@Captor
	private ArgumentCaptor<String> argumentCaptor;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	User user = User.builder().username("test").email("test@gmail.com").gender("MALE").password(encoder.encode("test"))
			.build();

	@Test
	@DisplayName("Should Load User By Username Successfully")
	void shouldLoadUserByUsernameSuccessfully() {

		/**
		 * Method stubbing
		 */

		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

		Optional<User> findByUsername = userRepository.findByUsername("test");

		/**
		 * verifies that findByUsername(string) method of UserRepository is invoked
		 * atleast once for the above operation
		 */

		Mockito.verify(userRepository, atLeastOnce()).findByUsername(argumentCaptor.capture());

		/**
		 * confirms the captured argument is same as given in the method invocation
		 */

		assertEquals("test", argumentCaptor.getValue());

		/**
		 * confirms the result is same as expected
		 */

		assertThat(findByUsername.get()).usingRecursiveComparison().ignoringFields("userId").isEqualTo(user);

	}

}
