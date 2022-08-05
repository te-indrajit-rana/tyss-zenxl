package com.ty.zenxl.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ty.zenxl.pojos.JwtUtils;
import com.ty.zenxl.request.SignUpRequest;
import com.ty.zenxl.request.UpdateUserRequest;
import com.ty.zenxl.response.UserResponse;
import com.ty.zenxl.response.ZenxlResponseBody;
import com.ty.zenxl.service.ZenxlCustomUserDetailsService;

@WebMvcTest(controllers = ZenxlUserController.class)
class ZenxlUserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ZenxlUserController zenxlUserController;

	@MockBean
	private ZenxlCustomUserDetailsService customUserDetailsService;

	@MockBean
	private PasswordEncoder encoder;

	@MockBean
	private JwtUtils jwtUtils;

	String jsonRequest = "{\"username\":\"test\",\"email\":\"test@gmail.com\",\"dateOfBirth\":1651729884421,\"gender\":\"MALE\",\"role\":\"ROLE_ADMIN\",\"password\":\"test\"}";
	String jsonUpdateUserRequest = "{\"username\":\"admin\",\"email\":\"admin@gmail.com\",\"dateOfBirth\":1651729884421,\"gender\":\"MALE\",\"role\":\"ROLE_ADMIN\"}";

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Add User To Admin Only")
	void shouldAllowAddUserToAdminOnly() throws Exception {

		when(zenxlUserController.addUser(any(SignUpRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.CREATED));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/zenxl/user/add-user")
				.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf());

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Find All Users To Admin Only")
	void shouldAllowFindAllUsersToAdminOnly() throws Exception {

		when(zenxlUserController.findAllUsers()).thenReturn(new ResponseEntity<List<UserResponse>>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(get("/api/v1/zenxl/user/find-all-users").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Update User To Admin Only")
	void shouldAllowUpdateUserToAdminOnly() throws Exception {

		when(zenxlUserController.updateUser(anyInt(), any(UpdateUserRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(
				put("/api/v1/zenxl/user/update-user").header("userId", 1).accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonUpdateUserRequest).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Delete User To Admin Only")
	void shouldAllowDeleteUserToAdminOnly() throws Exception {

		when(zenxlUserController.deleteUser(anyInt())).thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(
				delete("/api/v1/zenxl/user/delete-user").header("userId", 1).accept(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Change User Status To Admin Only")
	void shouldAllowChangeUserStatusToAdminOnly() throws Exception {

		when(zenxlUserController.setUserStatus(anyInt(), anyBoolean()))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(put("/api/v1/zenxl/user/change-user-status").header("userId", 1)
				.header("status", false).accept(MediaType.APPLICATION_JSON_VALUE).with(csrf())).andExpect(status().isOk())
				.andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

}
