package com.ty.zenxl.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ty.zenxl.request.ItemRequest;
import com.ty.zenxl.request.ItemUpdateRequest;
import com.ty.zenxl.response.CertificateResponse;
import com.ty.zenxl.response.ZenxlResponseBody;
import com.ty.zenxl.service.ZenxlItemService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * Zenxl item controller for master data.
 * 
 * <p>
 * CRUD apis for item or part details
 * </p>
 * 
 * <p>
 * Upload certificate, update certificate and view certificate for each item
 * </p>
 * 
 * @author Abhishek
 * @version 1.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/zenxl/item")
@SecurityRequirement(name = "zenxl-api")
public class ZenxlItemController {

	private final ZenxlItemService itemService;

	@PostMapping(value = "/upload-certificate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ZenxlResponseBody> uploadCertificate(@RequestPart MultipartFile file,
			@RequestHeader String data) throws IOException {
		return ResponseEntity.status(HttpStatus.CREATED).body(itemService.uploadCertificate(file, data));
	}

	@GetMapping(value = "/view-certificate", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Resource> viewCertificate(@RequestHeader String certificateNumber)
			throws MalformedURLException {
		CertificateResponse certificate = itemService.viewCertificate(certificateNumber);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.parseMediaType(certificate.getCertificateFormat()))
				.body(certificate.getResource());
	}

	@PutMapping(value = "/update-certificate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ZenxlResponseBody> updateCertificate(@RequestPart MultipartFile file,
			@RequestHeader String data) throws IOException {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.updateCertificate(file, data));
	}

	@PostMapping("/add-item")
	public ResponseEntity<ZenxlResponseBody> addItem(@Valid @RequestBody ItemRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(itemService.addItem(request));
	}

	@GetMapping("/view-item")
	public ResponseEntity<ZenxlResponseBody> viewItem(@RequestHeader Long serialNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.viewItemDetails(serialNumber));
	}

	@GetMapping("/view-all-items")
	public ResponseEntity<ZenxlResponseBody> viewAllItems() {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.viewAllItems());
	}

	@DeleteMapping("/delete-item")
	public ResponseEntity<ZenxlResponseBody> deleteItem(@RequestHeader Long serialNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.deleteItem(serialNumber));
	}

	@PutMapping("/update-item")
	public ResponseEntity<ZenxlResponseBody> updateItem(@Valid @RequestBody ItemUpdateRequest request,
			@RequestHeader Long serialNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.updateItem(request, serialNumber));
	}

}
