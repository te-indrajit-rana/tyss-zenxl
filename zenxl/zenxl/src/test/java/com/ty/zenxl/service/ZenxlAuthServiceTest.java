package com.ty.zenxl.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ty.zenxl.entity.Role;
import com.ty.zenxl.entity.User;
import com.ty.zenxl.pojos.ZenxlCustomUserDetails;
import com.ty.zenxl.pojos.JwtUtils;
import com.ty.zenxl.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ZenxlAuthServiceTest {

	@Mock
	private ZenxlCustomUserDetailsService userDetailsService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtUtils jwtUtils;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	User activeUser = User.builder().username("test").email("test@gmail.com").gender("MALE")
			.password(encoder.encode("test")).active(true).role(Role.builder().roleName("ROLE_TEST").build()).build();

	User inactiveUser = User.builder().username("test1").email("test1@gmail.com").gender("MALE")
			.password(encoder.encode("test1")).active(false).role(Role.builder().roleName("ROLE_TEST").build()).build();

	ZenxlCustomUserDetails customUserDetailsForActiveUser = new ZenxlCustomUserDetails(activeUser);
	ZenxlCustomUserDetails customUserDetailsForInactiveUser = new ZenxlCustomUserDetails(inactiveUser);

	/*
	 * Test case for allowing an user to login with valid credentials
	 */

	@Test
	@DisplayName("Should Allow User To Login Successfully With Valid Credentials")
	void shouldLoginSuccessllyWithValidCredentials() throws Exception {

		when(userDetailsService.loadUserByUsername(anyString())).thenReturn(customUserDetailsForActiveUser);

		UserDetails userDetails = userDetailsService.loadUserByUsername("test@gmail.com");

		assertThat(userDetails.getUsername()).isEqualTo("test@gmail.com");

		Authentication authenticateRequest = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
				userDetails.getPassword());

		when(authenticationManager.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
						userDetails.getPassword(), userDetails.getAuthorities()));

		Authentication authenticatedUser = authenticationManager.authenticate(authenticateRequest);

		assertTrue(authenticatedUser.isAuthenticated());
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		assertThat(authenticatedUser.getName()).isEqualTo("test@gmail.com");
		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
	}

}
