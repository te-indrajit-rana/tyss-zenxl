package com.ty.zenxl.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.ty.zenxl.pojos.JwtUtils;
import com.ty.zenxl.request.StatusRequest;
import com.ty.zenxl.request.UpdateStatusRequest;
import com.ty.zenxl.response.StatusCategoryResponse;
import com.ty.zenxl.response.ViewStatusResponse;
import com.ty.zenxl.response.ZenxlResponseBody;
import com.ty.zenxl.service.ZenxlCustomUserDetailsService;
import com.ty.zenxl.service.ZenxlStatusService;

@WebMvcTest(ZenxlStatusController.class)
class ZenxlStatusControllerTest {

	@MockBean
	private ZenxlStatusController zenxlStatusController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ZenxlStatusService zenxlStatusService;

	@MockBean
	private ZenxlCustomUserDetailsService customUserDetailsService;

	@MockBean
	private PasswordEncoder encoder;

	@MockBean
	private JwtUtils jwtUtils;

	private String jsonStatusRequest = "{\"statusName\":\"APPROVED\",\"description\":\"For Approval Of Items To Import\",\"statusCategory\":\"IMPORT\"}";
	private String jsonUpdateStatusRequest = "{\"statusName\":\"APPROVED\",\"description\":\"This Is For Approval Of Items To Export\",\"statusCategory\":\"EXPORT\"}";

	@Test
	@WithMockUser(roles = "ADMIN")
	void allowAddStatusToAdminOnly() throws Exception {

		when(zenxlStatusController.addStatus(any(StatusRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.CREATED));

		MvcResult result = mockMvc
				.perform(post("/api/v1/zenxl/status/add-status").accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonStatusRequest).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isCreated()).andReturn();
		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow View Status By Status Id To Admin Only")
	void shouldAllowViewStatusByStatusIdToAdminOnly() throws Exception {

		when(zenxlStatusController.viewStatus(anyString(), anyString()))
				.thenReturn(new ResponseEntity<ViewStatusResponse>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(get("/api/v1/zenxl/status/view-status").header("statusName", "APPROVED")
						.header("statusCategory", "IMPORT").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Find All Statuses To Admin Only")
	void shouldAllowFindAllStatusesToAdminOnly() throws Exception {

		when(zenxlStatusController.findAllStatuses())
				.thenReturn(new ResponseEntity<List<StatusCategoryResponse>>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(get("/api/v1/zenxl/status/find-all-statuses").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Update Status To Admin Only")
	void shouldAllowUpdateStatusToAdminOnly() throws Exception {

		when(zenxlStatusController.updateStatus(anyString(), anyString(), any(UpdateStatusRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(put("/api/v1/zenxl/status/update-status").header("statusName", "APPROVED")
						.header("statusCategory", "IMPORT").accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonUpdateStatusRequest).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Delete Status To Admin Only")
	void shouldAllowDeleteStatusToAdminOnly() throws Exception {

		when(zenxlStatusController.deleteStatus(anyString(), anyString()))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(delete("/api/v1/zenxl/status/delete-status").header("statusName", "APPROVED")
						.header("statusCategory", "IMPORT").accept(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Change Status To Admin Only")
	void shouldAllowChangeStatusToAdminOnly() throws Exception {

		when(zenxlStatusController.setStatus(anyString(), anyString(), anyBoolean()))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(put("/api/v1/zenxl/status/change-status").header("statusName", "APPROVED")
				.header("statusCategory", "IMPORT").header("isActive", false).accept(MediaType.APPLICATION_JSON_VALUE)
				.with(csrf())).andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

}
