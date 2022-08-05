package com.ty.zenxl.controller;

import static com.ty.zenxl.pojos.ZenxlConstantData.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ty.zenxl.request.StatusRequest;
import com.ty.zenxl.request.UpdateStatusRequest;
import com.ty.zenxl.response.StatusCategoryResponse;
import com.ty.zenxl.response.StatusResponse;
import com.ty.zenxl.response.ViewStatusResponse;
import com.ty.zenxl.response.ZenxlResponseBody;
import com.ty.zenxl.service.ZenxlStatusService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * Defines all the apis to perform CRUD operations related to statuses.
 * 
 * Permitted to be accessed by Admin.
 * 
 * @author Indrajit
 * @version 1.0
 *
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/zenxl/status")
@SecurityRequirement(name = "zenxl-api")
public class ZenxlStatusController {

	private final ZenxlStatusService zenxlStatusService;

	@PostMapping("/add-status")
	public ResponseEntity<ZenxlResponseBody> addStatus(@Valid @RequestBody StatusRequest request) {

		StatusResponse addStatus = zenxlStatusService.addStatus(request);
		ZenxlResponseBody zenxlResponseBody = ZenxlResponseBody.builder().isError(IS_ERROR_FALSE)
				.message(ADDED_SUCCESSFULLY).data(addStatus).build();
		return ResponseEntity.status(HttpStatus.CREATED).body(zenxlResponseBody);
	}

	@GetMapping("/view-status")
	public ResponseEntity<ViewStatusResponse> viewStatus(@RequestHeader String statusName,
			@RequestHeader String statusCategory) {
		return ResponseEntity.ok(zenxlStatusService.viewStatus(statusName, statusCategory));
	}

	@GetMapping("/find-all-statuses")
	public ResponseEntity<List<StatusCategoryResponse>> findAllStatuses() {
		return ResponseEntity.ok(zenxlStatusService.findAllStatuses());
	}

	@GetMapping("/find-all-status-categories")
	public ResponseEntity<List<String>> findAllStatusCategories() {
		return ResponseEntity.ok(zenxlStatusService.findAllStatusCategories());
	}
	
	@PutMapping("/update-status")
	public ResponseEntity<ZenxlResponseBody> updateStatus(@RequestHeader String statusName,
			@RequestHeader String statusCategory, @Valid @RequestBody UpdateStatusRequest request) {
		
		String updateStatusMessage = zenxlStatusService.updateStatus(statusName, statusCategory, request);
		ZenxlResponseBody zenxlResponseBody = ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(updateStatusMessage).build();
		return ResponseEntity.status(HttpStatus.OK).body(zenxlResponseBody);
	}

	@DeleteMapping("/delete-status")
	public ResponseEntity<ZenxlResponseBody> deleteStatus(@RequestHeader String statusName, @RequestHeader String statusCategory) {
		
		String deleteStatusMessage = zenxlStatusService.deleteStatus(statusName, statusCategory);
		ZenxlResponseBody zenxlResponseBody = ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(deleteStatusMessage).build();
		return ResponseEntity.status(HttpStatus.OK).body(zenxlResponseBody);
	}

	@PutMapping("/change-status")
	public ResponseEntity<ZenxlResponseBody> setStatus(@RequestHeader String statusName,
			@RequestHeader String statusCategory, @RequestHeader boolean isActive) {
		
		String setStatusMessage = zenxlStatusService.setStatus(statusName, statusCategory, isActive);
		ZenxlResponseBody zenxlResponseBody = ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(setStatusMessage).build();
		return ResponseEntity.status(HttpStatus.OK).body(zenxlResponseBody);
	}

}
