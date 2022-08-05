package com.ty.zenxl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ty.zenxl.pojos.ZenxlConstantData;
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

@ExtendWith(MockitoExtension.class)
class ZenxlUtilityServiceTest {

	@Mock
	private ZenxlUtilityService zenxlUtilityService;

	CertificateTypeRequest certificateTypeRequest = CertificateTypeRequest.builder().certificateType("TestCertificate").build();
	UpdateCertificateTypeRequest updateCertificateType = UpdateCertificateTypeRequest.builder()
			.certificateType("TestUpdateCertificate").build();
	CertificateTypeResponse certificateTypeResponse = CertificateTypeResponse.builder().certificateType("TestCertificate").build();

	CodeTypeRequest codeTypeRequest = CodeTypeRequest.builder().codeType("TestCode").build();
	UpdateCodeTypeRequest updateCodeType = UpdateCodeTypeRequest.builder().codeType("TestUpdateCode").build();
	CodeTypeResponse codeTypeResponse = CodeTypeResponse.builder().codeType("TestCode").build();

	IncotermTypeRequest incotermTypeRequest = IncotermTypeRequest.builder().incotermType("TestIncoterm").build();
	UpdateIncotermTypeRequest updateIncotermType = UpdateIncotermTypeRequest.builder().incotermType("TestUpdateIncoterm").build();
	IncotermTypeResponse incotermTypeResponse = IncotermTypeResponse.builder().incotermType("TestIncoterm").build();

	InspectionTypeRequest inspectionTypeRequest = InspectionTypeRequest.builder().inspectionType("TestInspection").build();
	UpdateInspectionTypeRequest updateInspectionType = UpdateInspectionTypeRequest.builder().inspectionType("TestUpdateInspection")
			.build();
	InspectionTypeResponse inspectionTypeResponse = InspectionTypeResponse.builder().inspectionType("TestInspection").build();
	
	HsCodeRequest hsCodeRequest = HsCodeRequest.builder().hsCodeType("TestHsCode").build();
	UpdateHsCodeRequest updateHsCode = UpdateHsCodeRequest.builder().hsCodeType("TestUpdateHsCode").build();
	HsCodeResponse hsCodeResponse = HsCodeResponse.builder().hsCodeType("TestHsCode").build();

	// certificate-type should cases

	@Test
	@DisplayName("Should Add CertificateType Successfully")
	void shouldAddCertificateTypeSuccessfully() {

		when(zenxlUtilityService.addCertificateType(any(CertificateTypeRequest.class))).thenReturn(certificateTypeResponse);
		CertificateTypeResponse response = zenxlUtilityService.addCertificateType(certificateTypeRequest);
		assertEquals(certificateTypeResponse.getCertificateType(), response.getCertificateType());
	}

	@Test
	@DisplayName("Should Find All CertificateTypes Successfully")
	void shouldFindAllCertificateTypesSuccessfully() {
		List<CertificateTypeResponse> list = List.of(certificateTypeResponse);
		when(zenxlUtilityService.findAllCertificateTypes()).thenReturn(list);
		List<CertificateTypeResponse> findAllCertificateTypes = zenxlUtilityService.findAllCertificateTypes();
		assertNotNull(findAllCertificateTypes);
		assertEquals(list.get(0), findAllCertificateTypes.get(0));
	}

	@Test
	@DisplayName("Should Update CertificateType Successfully")
	void shouldUpdateCertificateTypeSuccessfully() {
		when(zenxlUtilityService.updateCertificateType(anyInt(), any(UpdateCertificateTypeRequest.class)))
				.thenReturn(ZenxlConstantData.UPDATED_SUCCESSFULLY);

		String updateCertificateTypeMessage = zenxlUtilityService.updateCertificateType(1, updateCertificateType);
		assertEquals(ZenxlConstantData.UPDATED_SUCCESSFULLY, updateCertificateTypeMessage);
	}

	@Test
	@DisplayName("Should Delete CertificateType Successfully")
	void shouldDeleteCertificateTypeSuccessfully() {
		when(zenxlUtilityService.deleteCertificateType(anyInt())).thenReturn(ZenxlConstantData.DELETED_SUCCESSFULLY);
		String deleteCertificateTypeMsg = zenxlUtilityService.deleteCertificateType(1);
		assertEquals(ZenxlConstantData.DELETED_SUCCESSFULLY, deleteCertificateTypeMsg);
	}

	// code-type should cases

	@Test
	@DisplayName("Should Add CodeType Successfully")
	void shouldAddCodeTypeSuccessfully() {

		when(zenxlUtilityService.addCodeType(any(CodeTypeRequest.class))).thenReturn(codeTypeResponse);
		CodeTypeResponse response = zenxlUtilityService.addCodeType(codeTypeRequest);
		assertEquals(codeTypeResponse.getCodeType(), response.getCodeType());
	}

	@Test
	@DisplayName("Should Find All CodeTypes Successfully")
	void shouldFindAllCodeTypesSuccessfully() {
		List<CodeTypeResponse> list = List.of(codeTypeResponse);
		when(zenxlUtilityService.findAllCodeTypes()).thenReturn(list);
		List<CodeTypeResponse> findAllCodeTypes = zenxlUtilityService.findAllCodeTypes();
		assertNotNull(findAllCodeTypes);
		assertEquals(list.get(0), findAllCodeTypes.get(0));
	}

	@Test
	@DisplayName("Should Update CodeType Successfully")
	void shouldUpdateCodeTypeSuccessfully() {
		when(zenxlUtilityService.updateCodeType(anyInt(), any(UpdateCodeTypeRequest.class)))
				.thenReturn(ZenxlConstantData.UPDATED_SUCCESSFULLY);

		String updateCodeTypeMessage = zenxlUtilityService.updateCodeType(1, updateCodeType);
		assertEquals(ZenxlConstantData.UPDATED_SUCCESSFULLY, updateCodeTypeMessage);
	}

	@Test
	@DisplayName("Should Delete CodeType Successfully")
	void shouldDeleteCodeTypeSuccessfully() {

		when(zenxlUtilityService.deleteCodeType(anyInt())).thenReturn(ZenxlConstantData.DELETED_SUCCESSFULLY);
		String deleteCodeTypeMsg = zenxlUtilityService.deleteCodeType(1);
		assertEquals(ZenxlConstantData.DELETED_SUCCESSFULLY, deleteCodeTypeMsg);
	}

	// incoterm-type should cases

	@Test
	@DisplayName("Should Add IncotermType Successfully")
	void shouldAddIncotermTypeSuccessfully() {

		when(zenxlUtilityService.addIncotermType(any(IncotermTypeRequest.class))).thenReturn(incotermTypeResponse);
		IncotermTypeResponse response = zenxlUtilityService.addIncotermType(incotermTypeRequest);
		assertEquals(incotermTypeRequest.getIncotermType(), response.getIncotermType());
	}

	@Test
	@DisplayName("Should Find All IncotermTypes Successfully")
	void shouldFindAllIncotermTypesSuccessfully() {
		List<IncotermTypeResponse> list = List.of(incotermTypeResponse);
		when(zenxlUtilityService.findAllIncotermTypes()).thenReturn(list);
		List<IncotermTypeResponse> findAllIncotermTypes = zenxlUtilityService.findAllIncotermTypes();
		assertNotNull(findAllIncotermTypes);
		assertEquals(list.get(0), findAllIncotermTypes.get(0));
	}

	@Test
	@DisplayName("Should Update IncotermType Successfully")
	void shouldUpdateIncotermTypeSuccessfully() {
		when(zenxlUtilityService.updateIncotermType(anyInt(), any(UpdateIncotermTypeRequest.class)))
				.thenReturn(ZenxlConstantData.UPDATED_SUCCESSFULLY);

		String updateIncotermTypeMessage = zenxlUtilityService.updateIncotermType(1, updateIncotermType);
		assertEquals(ZenxlConstantData.UPDATED_SUCCESSFULLY, updateIncotermTypeMessage);
	}

	@Test
	@DisplayName("Should Delete IncotermType Successfully")
	void shouldDeleteIncotermTypeSuccessfully() {

		when(zenxlUtilityService.deleteIncotermType(anyInt())).thenReturn(ZenxlConstantData.DELETED_SUCCESSFULLY);
		String deleteIncotermMsg = zenxlUtilityService.deleteIncotermType(1);
		assertEquals(ZenxlConstantData.DELETED_SUCCESSFULLY, deleteIncotermMsg);
	}

	// inspection-type should cases

	@Test
	@DisplayName("Should Add InspectionType Successfully")
	void shouldAddInspectionTypeSuccessfully() {

		when(zenxlUtilityService.addInspectionType(any(InspectionTypeRequest.class))).thenReturn(inspectionTypeResponse);
		InspectionTypeResponse response = zenxlUtilityService.addInspectionType(inspectionTypeRequest);
		assertEquals(inspectionTypeResponse.getInspectionType(), response.getInspectionType());
	}

	@Test
	@DisplayName("Should Find All InspectionTypes Successfully")
	void shouldFindAllInspectionTypesSuccessfully() {
		List<InspectionTypeResponse> list = List.of(inspectionTypeResponse);
		when(zenxlUtilityService.findAllInspectionTypes()).thenReturn(list);
		List<InspectionTypeResponse> findAllInspectionTypes = zenxlUtilityService.findAllInspectionTypes();
		assertNotNull(findAllInspectionTypes);
		assertEquals(list.get(0), findAllInspectionTypes.get(0));
	}

	@Test
	@DisplayName("Should Update InspectionType Successfully")
	void shouldUpdateInspectionTypeSuccessfully() {
		when(zenxlUtilityService.updateInspectionType(anyInt(), any(UpdateInspectionTypeRequest.class)))
				.thenReturn(ZenxlConstantData.UPDATED_SUCCESSFULLY);

		String updateInspectionTypeMessage = zenxlUtilityService.updateInspectionType(1, updateInspectionType);
		assertEquals(ZenxlConstantData.UPDATED_SUCCESSFULLY, updateInspectionTypeMessage);
	}

	@Test
	@DisplayName("Should Delete InspectionType Successfully")
	void shouldDeleteInspectionTypeSuccessfully() {
		when(zenxlUtilityService.deleteInspectionType(anyInt())).thenReturn(ZenxlConstantData.DELETED_SUCCESSFULLY);
		String deleteInspectionMsg = zenxlUtilityService.deleteInspectionType(1);
		assertEquals(ZenxlConstantData.DELETED_SUCCESSFULLY, deleteInspectionMsg);
	}
	
	// hscode should cases

		@Test
		@DisplayName("Should Add HsCode Successfully")
		void shouldAddHsCodeSuccessfully() {

			when(zenxlUtilityService.addHsCode(any(HsCodeRequest.class))).thenReturn(hsCodeResponse);
			HsCodeResponse response = zenxlUtilityService.addHsCode(hsCodeRequest);
			assertEquals(hsCodeResponse.getHsCodeType(), response.getHsCodeType());
		}

		@Test
		@DisplayName("Should Find All HsCodes Successfully")
		void shouldFindAllHsCodesSuccessfully() {
			List<HsCodeResponse> list = List.of(hsCodeResponse);
			when(zenxlUtilityService.findAllHsCodes()).thenReturn(list);
			List<HsCodeResponse> findAllHsCodes = zenxlUtilityService.findAllHsCodes();
			assertNotNull(findAllHsCodes);
			assertEquals(list.get(0), findAllHsCodes.get(0));
		}

		@Test
		@DisplayName("Should Update HsCode Successfully")
		void shouldUpdateHsCodeSuccessfully() {
			when(zenxlUtilityService.updateHsCode(anyInt(), any(UpdateHsCodeRequest.class)))
					.thenReturn(ZenxlConstantData.UPDATED_SUCCESSFULLY);

			String updateHsCodeMessage = zenxlUtilityService.updateHsCode(1, updateHsCode);
			assertEquals(ZenxlConstantData.UPDATED_SUCCESSFULLY, updateHsCodeMessage);
		}

		@Test
		@DisplayName("Should Delete HsCode Successfully")
		void shouldDeleteHsCodeSuccessfully() {

			when(zenxlUtilityService.deleteHsCode(anyInt())).thenReturn(ZenxlConstantData.DELETED_SUCCESSFULLY);
			String deleteHsCodeMsg = zenxlUtilityService.deleteHsCode(1);
			assertEquals(ZenxlConstantData.DELETED_SUCCESSFULLY, deleteHsCodeMsg);
		}

}
