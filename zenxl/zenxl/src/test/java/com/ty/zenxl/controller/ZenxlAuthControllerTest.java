package com.ty.zenxl.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ty.zenxl.pojos.JwtUtils;
import com.ty.zenxl.request.ChangePasswordRequest;
import com.ty.zenxl.request.LoginRequest;
import com.ty.zenxl.request.SignUpRequest;
import com.ty.zenxl.response.ZenxlResponseBody;
import com.ty.zenxl.service.ZenxlCustomUserDetailsService;

@WebMvcTest(ZenxlAuthController.class)
class ZenxlAuthControllerTest {

	@MockBean
	private ZenxlAuthController zenxlAuthController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ZenxlCustomUserDetailsService customUserDetailsService;

	@MockBean
	private PasswordEncoder encoder;

	@MockBean
	private JwtUtils jwtUtils;

	LoginRequest loginRequest = LoginRequest.builder().email("test@gmail.com").password("test123").build();

	String jsonLoginRequest = "{\"email\":\"test@gmail.com\",\"password\":\"test123\"}";
	String jsonAdminRegistrationRequest = "{\"username\":\"admin\",\"email\":\"admin@gmail.com\",\"dateOfBirth\":1651729884421,\"gender\":\"MALE\",\"role\":\"ROLE_ADMIN\",\"password\":\"admin\"}";
	String jsonChangePasswordRequest = "{\"passcode\":123456,\"password\":\"test123\",\"confirmPassword\":\"test123\"}";

	@Test
	@DisplayName("Should Give Successful Login Response With Given Username and Password")
	void shouldGiveSuccessfulLoginResponse() throws Exception {

		when(zenxlAuthController.authenticateUser(any(LoginRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(post("/api/v1/zenxl/auth/login").accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonLoginRequest).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@DisplayName("Should Give Successful Admin Registration Response")
	void shouldGiveSuccessfulAdminRegistrationResponse() throws Exception {

		when(zenxlAuthController.adminRegistration(any(SignUpRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.CREATED));

		MvcResult result = mockMvc.perform(post("/api/v1/zenxl/auth/admin-registration")
				.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonAdminRegistrationRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf())).andExpect(status().isCreated())
				.andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@DisplayName("Should Give Successful Forgot Password Response")
	void shouldGiveSuccessfulForgotPasswordResponse() throws Exception {

		when(zenxlAuthController.forgotPassword(anyString()))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(get("/api/v1/zenxl/auth/forgot-password").header("email", "test@gmail.com")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@DisplayName("Should Give Successful Change Password Response")
	void shouldGiveSuccessfulChangePasswordResponse() throws Exception {

		when(zenxlAuthController.changePassword(anyString(), any(ChangePasswordRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(put("/api/v1/zenxl/auth/change-password").header("email", "test@gmail.com")
						.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonChangePasswordRequest)
						.contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

}
