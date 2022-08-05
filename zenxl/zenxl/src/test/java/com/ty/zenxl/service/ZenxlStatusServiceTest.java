package com.ty.zenxl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ty.zenxl.entity.Status;
import com.ty.zenxl.pojos.ZenxlConstantData;
import com.ty.zenxl.request.UpdateStatusRequest;
import com.ty.zenxl.response.StatusCategoryResponse;
import com.ty.zenxl.response.StatusResponse;
import com.ty.zenxl.response.ViewStatusResponse;

@ExtendWith(MockitoExtension.class)
class ZenxlStatusServiceTest {

	@Mock
	private ZenxlStatusService zenxlStatusService;

	Status status = Status.builder().statusName("APPROVED").description("For Approval Of Items To Import")
			.statusCategory("IMPORT").build();
	StatusResponse statusResponse = StatusResponse.builder().statusName("APPROVED")
			.description("For Approval Of Items To Import").isActive(true).build();

	List<StatusResponse> listOfStatusResponses = List.of(statusResponse);

	StatusCategoryResponse statusCategoryResponse = StatusCategoryResponse.builder().statusCategory("IMPORT")
			.statusResponses(listOfStatusResponses).build();
	List<StatusCategoryResponse> listOfStatusCategoryResponse = List.of(statusCategoryResponse);

	@Test
	@DisplayName("Should View Status By Status Id Successfully")
	void shouldViewStatusByStatusIdSuccessful() {
		when(zenxlStatusService.viewStatus(anyString(), anyString()))
				.thenReturn(ViewStatusResponse.builder().statusName("APPROVED").build());
		ViewStatusResponse viewStatus = zenxlStatusService.viewStatus("APPROVED", "IMPORT");
		assertNotNull(viewStatus);
		assertEquals(status.getStatusName(), viewStatus.getStatusName());
	}

	@Test
	@DisplayName("Should Find All Statuss Successfully")
	void shouldFindAllStatuses() {

		when(zenxlStatusService.findAllStatuses()).thenReturn(listOfStatusCategoryResponse);
		List<StatusCategoryResponse> findAllStatues = zenxlStatusService.findAllStatuses();
		assertNotNull(findAllStatues);
		assertEquals(listOfStatusCategoryResponse.get(0), findAllStatues.get(0));
	}

	@Test
	@DisplayName("Should Update Status Successfully")
	void shouldUpdateStatusSuccessful() {
		when(zenxlStatusService.updateStatus(anyString(), anyString(), any(UpdateStatusRequest.class)))
				.thenReturn(ZenxlConstantData.UPDATED_SUCCESSFULLY);

		UpdateStatusRequest updateStatusRequest = UpdateStatusRequest.builder().statusName("PENDING")
				.description("This is to notify pending for permission to import.").statusCategory("IMPORT").build();

		String updateStatusMessage = zenxlStatusService.updateStatus("PENDING", "IMPORT", updateStatusRequest);
		assertEquals(ZenxlConstantData.UPDATED_SUCCESSFULLY, updateStatusMessage);
	}

	@Test
	@DisplayName("Should Delete Status Successfully")
	void shouldDeleteStatusSuccessful() {
		String msg = "Successful";
		when(zenxlStatusService.deleteStatus(anyString(), anyString())).thenReturn(msg);
		String deleteUserMsg = zenxlStatusService.deleteStatus("PENDING", "IMPORT");
		assertEquals(msg, deleteUserMsg);
	}

	@Test
	@DisplayName("Should Change Status Successfully")
	void shouldChangeStatusSuccessful() {

		when(zenxlStatusService.setStatus(anyString(), anyString(), anyBoolean()))
				.thenReturn(ZenxlConstantData.STATUS_CHANGED_SUCCESSFULLY);
		String setStatusMessage = zenxlStatusService.setStatus("PENDING", "APPROVED", false);
		assertEquals(ZenxlConstantData.STATUS_CHANGED_SUCCESSFULLY, setStatusMessage);
	}

}
