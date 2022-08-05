package com.ty.zenxl.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.ty.zenxl.request.CertificateTypeRequest;
import com.ty.zenxl.request.CodeTypeRequest;
import com.ty.zenxl.request.HsCodeRequest;
import com.ty.zenxl.request.IncotermTypeRequest;
import com.ty.zenxl.request.InspectionTypeRequest;
import com.ty.zenxl.request.UpdateCertificateTypeRequest;
import com.ty.zenxl.request.UpdateCodeTypeRequest;
import com.ty.zenxl.request.UpdateHsCodeRequest;
import com.ty.zenxl.request.UpdateIncotermTypeRequest;
import com.ty.zenxl.request.UpdateInspectionTypeRequest;
import com.ty.zenxl.response.CertificateTypeResponse;
import com.ty.zenxl.response.CodeTypeResponse;
import com.ty.zenxl.response.HsCodeResponse;
import com.ty.zenxl.response.IncotermTypeResponse;
import com.ty.zenxl.response.InspectionTypeResponse;
import com.ty.zenxl.response.ZenxlResponseBody;
import com.ty.zenxl.service.ZenxlCustomUserDetailsService;

@WebMvcTest(ZenxlUtilityController.class)
class ZenxlUtilityControllerTest {

	@MockBean
	private ZenxlUtilityController zenxlUtilityController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ZenxlCustomUserDetailsService customUserDetailsService;

	@MockBean
	private PasswordEncoder encoder;

	@MockBean
	private JwtUtils jwtUtils;

	String jsonCertificateTypeRequest = "{\"certificateType\":\"TestCertificate\"}";
	String jsonUpdateCertificateTypeRequest = "{\"certificateType\":\"TestUpdateCertificate\"}";

	String jsonCodeTypeRequest = "{\"codeType\":\"TestCode\"}";
	String jsonUpdateCodeTypeRequest = "{\"codeType\":\"TestUpdateCode\"}";

	String jsonIncotermTypeRequest = "{\"incotermType\":\"TestIncoterm\"}";
	String jsonUpdateIncotermTypeRequest = "{\"incotermType\":\"TestUpdateIncoterm\"}";

	String jsonInspectionTypeRequest = "{\"inspectionType\":\"TestInspection\"}";
	String jsonUpdateInspectionTypeRequest = "{\"inspectionType\":\"TestUpdateInspection\"}";

	String jsonHsCodeTypeRequest = "{\"hsCodeType\":\"TestHsCode\"}";
	String jsonUpdateHsCodeTypeRequest = "{\"hsCodeType\":\"TestUpdateHsCode\"}";

	// certificate-type related test cases

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Add CertificateType To Admin Only")
	void shouldAllowAdminToAddCertificateTypeSuccessfully() throws Exception {

		when(zenxlUtilityController.addCertificateType(any(CertificateTypeRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.CREATED));

		MvcResult result = mockMvc
				.perform(post("/api/v1/zenxl/utility/add-certificate-type").accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonCertificateTypeRequest).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isCreated()).andReturn();

		assertThat(result.getResponse()).isNotNull();

	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Find All CertificateTypes To Admin Only")
	void shouldAllowToFindAllCertificateTypesToAdminOnly() throws Exception {

		when(zenxlUtilityController.findAllCertificateTypes())
				.thenReturn(new ResponseEntity<List<CertificateTypeResponse>>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(
				get("/api/v1/zenxl/utility/find-all-certificate-types").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Update CertificateType To Admin Only")
	void shouldAllowUpdateCertificateTypeToAdminOnly() throws Exception {

		when(zenxlUtilityController.updateCertificateType(anyInt(), any(UpdateCertificateTypeRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(put("/api/v1/zenxl/utility/update-certificate-type").header("certificateTypeId", 1)
						.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonUpdateCertificateTypeRequest)
						.contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Delete CertificateType To Admin Only")
	void shouldAllowDeleteCertificateTypeToAdminOnly() throws Exception {

		when(zenxlUtilityController.deleteCertificateType(anyInt()))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(delete("/api/v1/zenxl/utility/delete-certificate-type")
				.header("certificateTypeId", 1).accept(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	// code-type related test cases

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Add CodeType To Admin Only")
	void shouldAllowAdminToAddCodeTypeSuccessfully() throws Exception {

		when(zenxlUtilityController.addCodeType(any(CodeTypeRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.CREATED));

		MvcResult result = mockMvc
				.perform(post("/api/v1/zenxl/utility/add-code-type").accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonCodeTypeRequest).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isCreated()).andReturn();

		assertThat(result.getResponse()).isNotNull();

	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Find All CodeTypes To Admin Only")
	void shouldAllowToFindAllCodeTypesToAdminOnly() throws Exception {

		when(zenxlUtilityController.findAllCodeTypes())
				.thenReturn(new ResponseEntity<List<CodeTypeResponse>>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(get("/api/v1/zenxl/utility/find-all-code-types").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Update CodeType To Admin Only")
	void shouldAllowUpdateCodeTypeToAdminOnly() throws Exception {

		when(zenxlUtilityController.updateCodeType(anyInt(), any(UpdateCodeTypeRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(put("/api/v1/zenxl/utility/update-code-type").header("codeTypeId", 1)
						.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonUpdateCodeTypeRequest)
						.contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Delete CodeType To Admin Only")
	void shouldAllowDeleteCodeTypeToAdminOnly() throws Exception {

		when(zenxlUtilityController.deleteCodeType(anyInt()))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(delete("/api/v1/zenxl/utility/delete-code-type").header("codeTypeId", 1)
				.accept(MediaType.APPLICATION_JSON_VALUE).with(csrf())).andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	// incoterm-type related test cases

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Add IncotermType To Admin Only")
	void shouldAllowAdminToAddIncotermTypeSuccessfully() throws Exception {

		when(zenxlUtilityController.addIncotermType(any(IncotermTypeRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.CREATED));

		MvcResult result = mockMvc
				.perform(post("/api/v1/zenxl/utility/add-incoterm-type").accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonIncotermTypeRequest).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isCreated()).andReturn();

		assertThat(result.getResponse()).isNotNull();

	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Find All IncotermTypes To Admin Only")
	void shouldAllowToFindAllIncotermTypesToAdminOnly() throws Exception {

		when(zenxlUtilityController.findAllIncotermTypes())
				.thenReturn(new ResponseEntity<List<IncotermTypeResponse>>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(get("/api/v1/zenxl/utility/find-all-incoterm-types").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Update IncotermType To Admin Only")
	void shouldAllowUpdateIncotermTypeToAdminOnly() throws Exception {

		when(zenxlUtilityController.updateIncotermType(anyInt(), any(UpdateIncotermTypeRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(put("/api/v1/zenxl/utility/update-incoterm-type").header("incotermTypeId", 1)
						.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonUpdateIncotermTypeRequest)
						.contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Delete IncotermType To Admin Only")
	void shouldAllowDeleteIncotermTypeToAdminOnly() throws Exception {

		when(zenxlUtilityController.deleteIncotermType(anyInt()))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(delete("/api/v1/zenxl/utility/delete-incoterm-type")
				.header("incotermTypeId", 1).accept(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	// inspection-type related test cases

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Add InspectionType To Admin Only")
	void shouldAllowAdminToAddInspectionTypeSuccessfully() throws Exception {

		when(zenxlUtilityController.addInspectionType(any(InspectionTypeRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.CREATED));

		MvcResult result = mockMvc
				.perform(post("/api/v1/zenxl/utility/add-inspection-type").accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonInspectionTypeRequest).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isCreated()).andReturn();

		assertThat(result.getResponse()).isNotNull();

	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Find All InspectionTypes To Admin Only")
	void shouldAllowToFindAllInspectionTypesToAdminOnly() throws Exception {

		when(zenxlUtilityController.findAllInspectionTypes())
				.thenReturn(new ResponseEntity<List<InspectionTypeResponse>>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(
						get("/api/v1/zenxl/utility/find-all-inspection-types").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Update InspectionType To Admin Only")
	void shouldAllowUpdateInspectionTypeToAdminOnly() throws Exception {

		when(zenxlUtilityController.updateInspectionType(anyInt(), any(UpdateInspectionTypeRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(put("/api/v1/zenxl/utility/update-inspection-type").header("inspectionTypeId", 1)
						.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonUpdateInspectionTypeRequest)
						.contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Delete InspectionType To Admin Only")
	void shouldAllowDeleteInspectionTypeToAdminOnly() throws Exception {

		when(zenxlUtilityController.deleteCertificateType(anyInt()))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(delete("/api/v1/zenxl/utility/delete-inspection-type")
				.header("inspectionTypeId", 1).accept(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	// hs code related test cases

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Add HsCode To Admin Only")
	void shouldAllowAdminToAddHsCodeSuccessfully() throws Exception {

		when(zenxlUtilityController.addHsCode(any(HsCodeRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.CREATED));

		MvcResult result = mockMvc
				.perform(post("/api/v1/zenxl/utility/add-hscode").accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonHsCodeTypeRequest).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isCreated()).andReturn();

		assertThat(result.getResponse()).isNotNull();

	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Find All HsCodes To Admin Only")
	void shouldAllowToFindAllHsCodesToAdminOnly() throws Exception {

		when(zenxlUtilityController.findAllHsCodes())
				.thenReturn(new ResponseEntity<List<HsCodeResponse>>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(get("/api/v1/zenxl/utility/find-all-hscodes").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Update HsCode To Admin Only")
	void shouldAllowUpdateHsCodeToAdminOnly() throws Exception {

		when(zenxlUtilityController.updateHscode(anyInt(), any(UpdateHsCodeRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(put("/api/v1/zenxl/utility/update-hscode").header("hsCodeId", 1)
						.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonUpdateHsCodeTypeRequest)
						.contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Delete HsCode To Admin Only")
	void shouldAllowDeleteHsCodeToAdminOnly() throws Exception {

		when(zenxlUtilityController.deleteHsCode(anyInt()))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(delete("/api/v1/zenxl/utility/delete-hscode").header("hsCodeId", 1)
				.accept(MediaType.APPLICATION_JSON_VALUE).with(csrf())).andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

}
